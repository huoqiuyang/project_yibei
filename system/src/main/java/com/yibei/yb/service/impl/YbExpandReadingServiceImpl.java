package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbExpandReadingBo;
import com.yibei.yb.domain.vo.YbExpandReadingVo;
import com.yibei.yb.domain.YbExpandReading;
import com.yibei.yb.mapper.YbExpandReadingMapper;
import com.yibei.yb.service.IYbExpandReadingService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 拓展阅读Service业务层处理
 *
 * @author yibei
 * @date 2022-05-11
 */
@Service
public class YbExpandReadingServiceImpl extends ServicePlusImpl<YbExpandReadingMapper, YbExpandReading, YbExpandReadingVo> implements IYbExpandReadingService {


    @Override
    public YbExpandReadingVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbExpandReadingVo> queryPageList(YbExpandReadingBo bo) {
        PagePlus<YbExpandReading, YbExpandReadingVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbExpandReadingVo> queryList(YbExpandReadingBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbExpandReading> buildQueryWrapper(YbExpandReadingBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbExpandReading> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(YbExpandReading::getSort);
        lqw.orderByDesc(YbExpandReading::getCreateTime);
//        lqw.orderByDesc(YbExpandReading::getId);
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), YbExpandReading::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getDescribe()), YbExpandReading::getDescribe, bo.getDescribe());
        lqw.like(StringUtils.isNotBlank(bo.getLabel()), YbExpandReading::getLabel, bo.getLabel());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrl()), YbExpandReading::getImgUrl, bo.getImgUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getAudioFrequency()), YbExpandReading::getAudioFrequency, bo.getAudioFrequency());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), YbExpandReading::getContent, bo.getContent());
        lqw.eq(bo.getSort() != null, YbExpandReading::getSort, bo.getSort());
        lqw.eq(bo.getStatus() != null, YbExpandReading::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbExpandReadingBo bo) {
        YbExpandReading add = BeanUtil.toBean(bo, YbExpandReading.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbExpandReadingBo bo) {
        YbExpandReading update = BeanUtil.toBean(bo, YbExpandReading.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbExpandReading entity){
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
    public YbExpandReadingVo queryExtData(YbExpandReadingVo vo) {
        List<YbExpandReadingVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbExpandReadingVo> queryExtData(List<YbExpandReadingVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }
}
