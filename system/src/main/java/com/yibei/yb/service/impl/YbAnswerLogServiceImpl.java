package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.User;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.service.IUserService;
import com.yibei.yb.domain.YbQuestionBankContent;
import com.yibei.yb.domain.vo.YbQuestionBankContentVo;
import com.yibei.yb.service.IYbQuestionBankContentService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbAnswerLogBo;
import com.yibei.yb.domain.vo.YbAnswerLogVo;
import com.yibei.yb.domain.YbAnswerLog;
import com.yibei.yb.mapper.YbAnswerLogMapper;
import com.yibei.yb.service.IYbAnswerLogService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 答题记录Service业务层处理
 *
 * @author yibei
 * @date 2022-05-07
 */
@Service
public class YbAnswerLogServiceImpl extends ServicePlusImpl<YbAnswerLogMapper, YbAnswerLog, YbAnswerLogVo> implements IYbAnswerLogService {

    @Resource
    private IYbQuestionBankContentService iYbQuestionBankContentService;
    @Resource
    private IUserService iUserService;

    @Override
    public YbAnswerLogVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbAnswerLogVo> queryPageList(YbAnswerLogBo bo) {
        PagePlus<YbAnswerLog, YbAnswerLogVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbAnswerLogVo> queryList(YbAnswerLogBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbAnswerLog> buildQueryWrapper(YbAnswerLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbAnswerLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbAnswerLog::getId);
        lqw.eq(bo.getQuestionBankContentId() != null, YbAnswerLog::getQuestionBankContentId, bo.getQuestionBankContentId());
        lqw.eq(bo.getUserId() != null, YbAnswerLog::getUserId, bo.getUserId());
        lqw.eq(bo.getType() != null, YbAnswerLog::getType, bo.getType());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbAnswerLogBo bo) {
        YbAnswerLog add = BeanUtil.toBean(bo, YbAnswerLog.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbAnswerLogBo bo) {
        YbAnswerLog update = BeanUtil.toBean(bo, YbAnswerLog.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbAnswerLog entity){
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
    public YbAnswerLogVo queryExtData(YbAnswerLogVo vo) {
        List<YbAnswerLogVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbAnswerLogVo> queryExtData(List<YbAnswerLogVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String questionBankContentIds = list.stream().map(s -> s.getQuestionBankContentId().toString()).collect(Collectors.joining(","));
        questionBankContentIds = StringUtils.isBlank(questionBankContentIds)?"0":questionBankContentIds;
        List<YbQuestionBankContentVo> ybQuestionBankContentVoList = iYbQuestionBankContentService.listVo(new LambdaQueryWrapper<YbQuestionBankContent>().inSql(YbQuestionBankContent::getId, questionBankContentIds));
        list.forEach(item->{
        item.getExtData().put("ybQuestionBankContent", ybQuestionBankContentVoList.stream().filter(s -> s.getId().compareTo(item.getQuestionBankContentId()) == 0).findFirst().orElse(null));
        });
        String userIds = list.stream().map(s -> s.getUserId().toString()).collect(Collectors.joining(","));
        userIds = StringUtils.isBlank(userIds)?"0":userIds;
        List<UserVo> userVoList = iUserService.listVo(new LambdaQueryWrapper<User>().inSql(User::getId, userIds));
        list.forEach(item->{
        item.getExtData().put("user", userVoList.stream().filter(s -> s.getId().compareTo(item.getUserId()) == 0).findFirst().orElse(null));
        });

        return list;
    }
}
