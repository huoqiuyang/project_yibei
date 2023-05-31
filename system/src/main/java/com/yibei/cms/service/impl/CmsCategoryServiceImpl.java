package com.yibei.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.cms.domain.bo.CmsCategoryBo;
import com.yibei.cms.domain.vo.CmsCategoryVo;
import com.yibei.cms.domain.CmsCategory;
import com.yibei.cms.mapper.CmsCategoryMapper;
import com.yibei.cms.service.ICmsCategoryService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 内容分类Service业务层处理
 *
 * @author yibei
 * @date 2022-01-08
 */
@Service
public class CmsCategoryServiceImpl extends ServicePlusImpl<CmsCategoryMapper, CmsCategory, CmsCategoryVo> implements ICmsCategoryService {


    @Override
    public CmsCategoryVo queryById(Long id){
        return getVoById(id);
    }


    @Override
    public List<CmsCategoryVo> queryList(CmsCategoryBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<CmsCategory> buildQueryWrapper(CmsCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CmsCategory> lqw = Wrappers.lambdaQuery();
//        lqw.orderByDesc(CmsCategory::getId);
        lqw.eq(bo.getParentId() != null, CmsCategory::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), CmsCategory::getName, bo.getName());
        lqw.like(StringUtils.isNotBlank(bo.getClassName()), CmsCategory::getClassName, bo.getClassName());
        lqw.eq(StringUtils.isNotBlank(bo.getCode()), CmsCategory::getCode, bo.getCode());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrl()), CmsCategory::getImgUrl, bo.getImgUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getImgsUrl()), CmsCategory::getImgsUrl, bo.getImgsUrl());
        lqw.eq(bo.getWeight() != null, CmsCategory::getWeight, bo.getWeight());
        lqw.eq(StringUtils.isNotBlank(bo.getCustomProperty()), CmsCategory::getCustomProperty, bo.getCustomProperty());
        lqw.eq(StringUtils.isNotBlank(bo.getBak()), CmsCategory::getBak, bo.getBak());
        lqw.eq(StringUtils.isNotBlank(bo.getBak2()), CmsCategory::getBak2, bo.getBak2());
        lqw.eq(bo.getStatus() != null, CmsCategory::getStatus, bo.getStatus());
        lqw.eq(bo.getCreateDataEnable() != null, CmsCategory::getCreateDataEnable, bo.getCreateDataEnable());
        lqw.eq(bo.getCreateChildEnable() != null, CmsCategory::getCreateChildEnable, bo.getCreateChildEnable());
        lqw.eq(bo.getChildTemplateId() != null, CmsCategory::getChildTemplateId, bo.getChildTemplateId());
        return lqw;
    }

    @Override
    public Boolean insertByBo(CmsCategoryBo bo) {
        CmsCategory add = BeanUtil.toBean(bo, CmsCategory.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(CmsCategoryBo bo) {
        CmsCategory update = BeanUtil.toBean(bo, CmsCategory.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(CmsCategory entity){
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
    public CmsCategoryVo queryExtData(CmsCategoryVo vo) {
        List<CmsCategoryVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<CmsCategoryVo> queryExtData(List<CmsCategoryVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }
}
