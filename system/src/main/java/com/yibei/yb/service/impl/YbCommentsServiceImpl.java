package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.User;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.service.IUserService;
import com.yibei.yb.domain.YbUpvoteLog;
import com.yibei.yb.service.IYbUpvoteLogService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbCommentsBo;
import com.yibei.yb.domain.vo.YbCommentsVo;
import com.yibei.yb.domain.YbComments;
import com.yibei.yb.mapper.YbCommentsMapper;
import com.yibei.yb.service.IYbCommentsService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 评论Service业务层处理
 *
 * @author yibei
 * @date 2022-05-19
 */
@Service
public class YbCommentsServiceImpl extends ServicePlusImpl<YbCommentsMapper, YbComments, YbCommentsVo> implements IYbCommentsService {

    @Resource
    private IUserService iUserService;

    @Resource
    private IYbUpvoteLogService iYbUpvoteLogService;

    @Override
    public YbCommentsVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbCommentsVo> queryPageList(YbCommentsBo bo) {
        PagePlus<YbComments, YbCommentsVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbCommentsVo> queryList(YbCommentsBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbComments> buildQueryWrapper(YbCommentsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbComments> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbComments::getIsTopping);
        lqw.orderByDesc(YbComments::getCreateTime);
//        lqw.orderByDesc(YbComments::getId);
        lqw.eq(bo.getUserId() != null, YbComments::getUserId, bo.getUserId());
        lqw.eq(bo.getContentId() != null, YbComments::getContentId, bo.getContentId());
        lqw.eq(bo.getParentId() != null, YbComments::getParentId, bo.getParentId());
        lqw.eq(bo.getSourceType() != null, YbComments::getSourceType, bo.getSourceType());
        lqw.eq(StringUtils.isNotBlank(bo.getComments()), YbComments::getComments, bo.getComments());
        lqw.eq(bo.getIsTopping() != null, YbComments::getIsTopping, bo.getIsTopping());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbCommentsBo bo) {
        YbComments add = BeanUtil.toBean(bo, YbComments.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbCommentsBo bo) {
        YbComments update = BeanUtil.toBean(bo, YbComments.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbComments entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public YbCommentsVo queryExtData(YbCommentsVo vo) {
        List<YbCommentsVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbCommentsVo> queryExtData(List<YbCommentsVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String userIds = list.stream().map(s -> s.getUserId().toString()).collect(Collectors.joining(","));
        userIds = StringUtils.isBlank(userIds)?"0":userIds;
        List<UserVo> userVoList = iUserService.listVo(new LambdaQueryWrapper<User>().inSql(User::getId, userIds));
        list.forEach(item->{
            item.getExtData().put("user", userVoList.stream().filter(s -> s.getId().compareTo(item.getUserId()) == 0).findFirst().orElse(null));
            item.getExtData().put("upvoteNum",iYbUpvoteLogService.count(new LambdaQueryWrapper<YbUpvoteLog>().eq(YbUpvoteLog::getCommentsId, item.getId())));
            item.getExtData().put("replyNum",count(new LambdaQueryWrapper<YbComments>().eq(YbComments::getParentId, item.getId())));
        });

        return list;
    }
}
