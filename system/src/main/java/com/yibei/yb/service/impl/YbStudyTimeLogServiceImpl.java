package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.yb.domain.clientVo.TranscendVo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbStudyTimeLogBo;
import com.yibei.yb.domain.vo.YbStudyTimeLogVo;
import com.yibei.yb.domain.YbStudyTimeLog;
import com.yibei.yb.mapper.YbStudyTimeLogMapper;
import com.yibei.yb.service.IYbStudyTimeLogService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 学习时间记录Service业务层处理
 *
 * @author yibei
 * @date 2022-05-18
 */
@Service
public class YbStudyTimeLogServiceImpl extends ServicePlusImpl<YbStudyTimeLogMapper, YbStudyTimeLog, YbStudyTimeLogVo> implements IYbStudyTimeLogService {

    @Override
    public YbStudyTimeLogVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbStudyTimeLogVo> queryPageList(YbStudyTimeLogBo bo) {
        PagePlus<YbStudyTimeLog, YbStudyTimeLogVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbStudyTimeLogVo> queryList(YbStudyTimeLogBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbStudyTimeLog> buildQueryWrapper(YbStudyTimeLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbStudyTimeLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbStudyTimeLog::getId);
        lqw.eq(bo.getUserId() != null, YbStudyTimeLog::getUserId, bo.getUserId());
        lqw.eq(bo.getType() != null, YbStudyTimeLog::getType, bo.getType());
        lqw.eq(bo.getContentId() != null, YbStudyTimeLog::getContentId, bo.getContentId());
        lqw.eq(bo.getMin() != null, YbStudyTimeLog::getMin, bo.getMin());
        lqw.eq(StringUtils.isNotBlank(bo.getDateDay()), YbStudyTimeLog::getDateDay, bo.getDateDay());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbStudyTimeLogBo bo) {
        YbStudyTimeLog add = BeanUtil.toBean(bo, YbStudyTimeLog.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbStudyTimeLogBo bo) {
        YbStudyTimeLog update = BeanUtil.toBean(bo, YbStudyTimeLog.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbStudyTimeLog entity){
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
    public YbStudyTimeLogVo queryExtData(YbStudyTimeLogVo vo) {
        List<YbStudyTimeLogVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbStudyTimeLogVo> queryExtData(List<YbStudyTimeLogVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }

    @Override
    public Integer getUserStudyMin(Long userId, String startTime, String endTime) {
        Integer min = baseMapper.getUserStudyMin(userId,startTime,endTime);
        if(min == null){
            min = 0;
        }
        return min;
    }

    @Override
    public TranscendVo getTranscendInfo(Long userId, String startTime, String endTime,int userStudyMin) {
        return baseMapper.getTranscendInfo(userId,startTime,endTime,userStudyMin);
    }
}
