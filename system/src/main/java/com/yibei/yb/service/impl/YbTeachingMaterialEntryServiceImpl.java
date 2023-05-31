package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yibei.yb.domain.YbTeachingMaterial;
import com.yibei.yb.domain.clientVo.*;
import com.yibei.yb.domain.vo.YbTeachingMaterialVo;
import com.yibei.yb.service.IYbTeachingMaterialService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbTeachingMaterialEntryBo;
import com.yibei.yb.domain.vo.YbTeachingMaterialEntryVo;
import com.yibei.yb.domain.YbTeachingMaterialEntry;
import com.yibei.yb.mapper.YbTeachingMaterialEntryMapper;
import com.yibei.yb.service.IYbTeachingMaterialEntryService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 教材词条Service业务层处理
 *
 * @author yibei
 * @date 2022-05-14
 */
@Service
public class YbTeachingMaterialEntryServiceImpl extends ServicePlusImpl<YbTeachingMaterialEntryMapper, YbTeachingMaterialEntry, YbTeachingMaterialEntryVo> implements IYbTeachingMaterialEntryService {

    @Resource
    private IYbTeachingMaterialService iYbTeachingMaterialService;

    @Override
    public YbTeachingMaterialEntryVo queryById(Long id){
        return getVoById(id);
    }


    @Override
    public List<YbTeachingMaterialEntryVo> queryList(YbTeachingMaterialEntryBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    @Override
    public List<YbTeachingMaterialEntryTitleVo> selectList(YbTeachingMaterialEntryBo bo) {
        List<YbTeachingMaterialEntryVo> list = listVo(buildQueryWrapper(bo));
        return BeanUtil.copyToList(list,YbTeachingMaterialEntryTitleVo.class);
    }

    private LambdaQueryWrapper<YbTeachingMaterialEntry> buildQueryWrapper(YbTeachingMaterialEntryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbTeachingMaterialEntry> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(YbTeachingMaterialEntry::getSort);
        lqw.orderByDesc(YbTeachingMaterialEntry::getCreateTime);
//        lqw.orderByDesc(YbTeachingMaterialEntry::getId);
        lqw.eq(bo.getTeachingMaterialId() != null, YbTeachingMaterialEntry::getTeachingMaterialId, bo.getTeachingMaterialId());
        lqw.eq(bo.getParentId() != null, YbTeachingMaterialEntry::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), YbTeachingMaterialEntry::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getLabel()), YbTeachingMaterialEntry::getLabel, bo.getLabel());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrl()), YbTeachingMaterialEntry::getImgUrl, bo.getImgUrl());
        lqw.eq(bo.getImportance() != null, YbTeachingMaterialEntry::getImportance, bo.getImportance());
        lqw.eq(StringUtils.isNotBlank(bo.getAudioFrequency()), YbTeachingMaterialEntry::getAudioFrequency, bo.getAudioFrequency());
        lqw.eq(StringUtils.isNotBlank(bo.getRelatedLinks()), YbTeachingMaterialEntry::getRelatedLinks, bo.getRelatedLinks());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), YbTeachingMaterialEntry::getContent, bo.getContent());
        lqw.eq(bo.getSort() != null, YbTeachingMaterialEntry::getSort, bo.getSort());
        lqw.eq(StringUtils.isNotBlank(bo.getKeyWord()), YbTeachingMaterialEntry::getKeyWord, bo.getKeyWord());
        lqw.eq(bo.getStatus() != null, YbTeachingMaterialEntry::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbTeachingMaterialEntryBo bo) {
        YbTeachingMaterialEntry add = BeanUtil.toBean(bo, YbTeachingMaterialEntry.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbTeachingMaterialEntryBo bo) {
        YbTeachingMaterialEntry update = BeanUtil.toBean(bo, YbTeachingMaterialEntry.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbTeachingMaterialEntry entity){
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
    public YbTeachingMaterialEntryVo queryExtData(YbTeachingMaterialEntryVo vo) {
        List<YbTeachingMaterialEntryVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbTeachingMaterialEntryVo> queryExtData(List<YbTeachingMaterialEntryVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String teachingMaterialIds = list.stream().map(s -> s.getTeachingMaterialId().toString()).collect(Collectors.joining(","));
        teachingMaterialIds = StringUtils.isBlank(teachingMaterialIds)?"0":teachingMaterialIds;
        List<YbTeachingMaterialVo> ybTeachingMaterialVoList = iYbTeachingMaterialService.listVo(new LambdaQueryWrapper<YbTeachingMaterial>().inSql(YbTeachingMaterial::getId, teachingMaterialIds));
        list.forEach(item->{
            item.getExtData().put("ybTeachingMaterial", ybTeachingMaterialVoList.stream().filter(s -> s.getId().compareTo(item.getTeachingMaterialId()) == 0).findFirst().orElse(null));
        });

        return list;
    }

    @Override
    public List<YbTmeCatalogueInfoVo> getCollectionList(Long userId, Long teachingMaterialId) {
        return baseMapper.getCollectionList(userId,teachingMaterialId);
    }

    @Override
    public List<YbTmeCatalogueSecondInfoVo> getSecondList(Long userId, Long teachingMaterialEntryId, Integer isCollection) {
        return baseMapper.getSecondList(userId,teachingMaterialEntryId,isCollection);
    }

    @Override
    public YbTeachingMaterialEntry getEntryLog(Long teachingMaterialId, Long userId, int learningType) {
        return baseMapper.getEntryLog(teachingMaterialId,userId,learningType);
    }

    @Override
    public YbTeachingMaterialEntryReadInfoVo getEntryReadInfo(Long teachingMaterialId, Long selectId) {
        return baseMapper.getEntryReadInfo(teachingMaterialId,selectId);
    }

    @Override
    public long getProportionNum(Long teachingMaterialId, Long userId) {
        return baseMapper.getProportionNum(teachingMaterialId,userId);
    }

    @Override
    public long getIsReciteNum(Long teachingMaterialId, Long userId) {
        return baseMapper.getIsReciteNum(teachingMaterialId,userId);
    }

    @Override
    public List<YbTeachingMaterialEntryReciteInfoVo> getToDayRecitePlanList(Long userId, Long teachingMaterialId, Integer studyLimit, Integer reviewLimit) {
        return baseMapper.getToDayRecitePlanList(userId,teachingMaterialId,studyLimit,reviewLimit);
    }

    @Override
    public int getReciteEntryList(Long userId, Long teachingMaterialId) {
        return baseMapper.getReciteEntryList(userId,teachingMaterialId);
    }
}