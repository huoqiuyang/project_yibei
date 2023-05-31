package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.User;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.service.IUserService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbUpvoteLogBo;
import com.yibei.yb.domain.vo.YbUpvoteLogVo;
import com.yibei.yb.domain.YbUpvoteLog;
import com.yibei.yb.mapper.YbUpvoteLogMapper;
import com.yibei.yb.service.IYbUpvoteLogService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 点赞记录Service业务层处理
 *
 * @author yibei
 * @date 2022-05-19
 */
@Service
public class YbUpvoteLogServiceImpl extends ServicePlusImpl<YbUpvoteLogMapper, YbUpvoteLog, YbUpvoteLogVo> implements IYbUpvoteLogService {

    @Resource
    private IUserService iUserService;

    @Override
    public YbUpvoteLogVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbUpvoteLogVo> queryPageList(YbUpvoteLogBo bo) {
        PagePlus<YbUpvoteLog, YbUpvoteLogVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbUpvoteLogVo> queryList(YbUpvoteLogBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbUpvoteLog> buildQueryWrapper(YbUpvoteLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbUpvoteLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbUpvoteLog::getId);
        lqw.eq(bo.getUserId() != null, YbUpvoteLog::getUserId, bo.getUserId());
        lqw.eq(bo.getCommentsId() != null, YbUpvoteLog::getCommentsId, bo.getCommentsId());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbUpvoteLogBo bo) {
        YbUpvoteLog add = BeanUtil.toBean(bo, YbUpvoteLog.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbUpvoteLogBo bo) {
        YbUpvoteLog update = BeanUtil.toBean(bo, YbUpvoteLog.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbUpvoteLog entity){
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
    public YbUpvoteLogVo queryExtData(YbUpvoteLogVo vo) {
        List<YbUpvoteLogVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbUpvoteLogVo> queryExtData(List<YbUpvoteLogVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String userIds = list.stream().map(s -> s.getUserId().toString()).collect(Collectors.joining(","));
        userIds = StringUtils.isBlank(userIds)?"0":userIds;
        List<UserVo> userVoList = iUserService.listVo(new LambdaQueryWrapper<User>().inSql(User::getId, userIds));
        list.forEach(item->{
        item.getExtData().put("user", userVoList.stream().filter(s -> s.getId().compareTo(item.getUserId()) == 0).findFirst().orElse(null));
        });

        return list;
    }
}
