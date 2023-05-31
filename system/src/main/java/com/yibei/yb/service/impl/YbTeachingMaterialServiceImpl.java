package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.yb.domain.clientVo.*;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbTeachingMaterialBo;
import com.yibei.yb.domain.vo.YbTeachingMaterialVo;
import com.yibei.yb.domain.YbTeachingMaterial;
import com.yibei.yb.mapper.YbTeachingMaterialMapper;
import com.yibei.yb.service.IYbTeachingMaterialService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 教材Service业务层处理
 *
 * @author yibei
 * @date 2022-05-11
 */
@Service
public class YbTeachingMaterialServiceImpl extends ServicePlusImpl<YbTeachingMaterialMapper, YbTeachingMaterial, YbTeachingMaterialVo> implements IYbTeachingMaterialService {


    @Override
    public YbTeachingMaterialVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbTeachingMaterialVo> queryPageList(YbTeachingMaterialBo bo) {
        PagePlus<YbTeachingMaterial, YbTeachingMaterialVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbTeachingMaterialVo> queryList(YbTeachingMaterialBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbTeachingMaterial> buildQueryWrapper(YbTeachingMaterialBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbTeachingMaterial> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(YbTeachingMaterial::getSort);
        lqw.orderByDesc(YbTeachingMaterial::getCreateTime);
//        lqw.orderByDesc(YbTeachingMaterial::getId);
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), YbTeachingMaterial::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrl()), YbTeachingMaterial::getImgUrl, bo.getImgUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getDescribe()), YbTeachingMaterial::getDescribe, bo.getDescribe());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), YbTeachingMaterial::getContent, bo.getContent());
        lqw.eq(bo.getSort() != null, YbTeachingMaterial::getSort, bo.getSort());
        lqw.eq(bo.getStatus() != null, YbTeachingMaterial::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbTeachingMaterialBo bo) {
        YbTeachingMaterial add = BeanUtil.toBean(bo, YbTeachingMaterial.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbTeachingMaterialBo bo) {
        YbTeachingMaterial update = BeanUtil.toBean(bo, YbTeachingMaterial.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbTeachingMaterial entity){
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
    public YbTeachingMaterialVo queryExtData(YbTeachingMaterialVo vo) {
        List<YbTeachingMaterialVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbTeachingMaterialVo> queryExtData(List<YbTeachingMaterialVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }

    @Override
    public List<ReportStudyReadVo> getReportReadList(Long userId) {
        return baseMapper.getReportReadList(userId);
    }

    @Override
    public List<ReportStudyReciteVo> getReportReciteList(Long userId) {
        return baseMapper.getReportReciteList(userId);
    }

    @Override
    public List<ReportStudyQuestionBankVo> getReportQuestionBankList(Long userId) {
        return baseMapper.getReportQuestionBankList(userId);
    }

    @Override
    public List<ReportStudyExpandReadingVo> getReportExpandReadingList(Long userId) {
        return baseMapper.getReportExpandReadingList(userId);
    }

    @Override
    public List<ReportStudyLogInfoVo> getReportStudyLogList(Long userId, int size) {
        return baseMapper.getReportStudyLogList(userId,size);
    }

}
