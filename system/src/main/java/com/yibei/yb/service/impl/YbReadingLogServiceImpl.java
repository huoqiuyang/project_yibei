package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.yb.domain.YbExpandReading;
import com.yibei.yb.domain.vo.YbExpandReadingVo;
import com.yibei.yb.service.IYbExpandReadingService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbReadingLogBo;
import com.yibei.yb.domain.vo.YbReadingLogVo;
import com.yibei.yb.domain.YbReadingLog;
import com.yibei.yb.mapper.YbReadingLogMapper;
import com.yibei.yb.service.IYbReadingLogService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 阅读记录Service业务层处理
 *
 * @author yibei
 * @date 2022-05-11
 */
@Service
public class YbReadingLogServiceImpl extends ServicePlusImpl<YbReadingLogMapper, YbReadingLog, YbReadingLogVo> implements IYbReadingLogService {

    @Resource
    private IYbExpandReadingService iYbExpandReadingService;

    @Override
    public YbReadingLogVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbReadingLogVo> queryPageList(YbReadingLogBo bo) {
        PagePlus<YbReadingLog, YbReadingLogVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbReadingLogVo> queryList(YbReadingLogBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbReadingLog> buildQueryWrapper(YbReadingLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbReadingLog> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbReadingLog::getId);
        lqw.eq(bo.getExpandReadId() != null, YbReadingLog::getExpandReadId, bo.getExpandReadId());
        lqw.eq(bo.getUserId() != null, YbReadingLog::getUserId, bo.getUserId());
        lqw.eq(bo.getStatus() != null, YbReadingLog::getStatus, bo.getStatus());
        lqw.eq(bo.getUpdateTime() != null, YbReadingLog::getUpdateTime, bo.getUpdateTime());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbReadingLogBo bo) {
        YbReadingLog add = BeanUtil.toBean(bo, YbReadingLog.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbReadingLogBo bo) {
        YbReadingLog update = BeanUtil.toBean(bo, YbReadingLog.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbReadingLog entity){
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
    public YbReadingLogVo queryExtData(YbReadingLogVo vo) {
        List<YbReadingLogVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbReadingLogVo> queryExtData(List<YbReadingLogVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String expandReadIds = list.stream().map(s -> s.getExpandReadId().toString()).collect(Collectors.joining(","));
        expandReadIds = StringUtils.isBlank(expandReadIds)?"0":expandReadIds;
        List<YbExpandReadingVo> ybExpandReadingVoList = iYbExpandReadingService.listVo(new LambdaQueryWrapper<YbExpandReading>().inSql(YbExpandReading::getId, expandReadIds));
        list.forEach(item->{
        item.getExtData().put("ybExpandReading", ybExpandReadingVoList.stream().filter(s -> s.getId().compareTo(item.getExpandReadId()) == 0).findFirst().orElse(null));
        });

        return list;
    }
}
