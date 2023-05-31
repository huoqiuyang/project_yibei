package com.yibei.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.PageUtils;
import com.yibei.system.domain.CityRegion;
import com.yibei.system.domain.bo.CityRegionAddBo;
import com.yibei.system.domain.bo.CityRegionEditBo;
import com.yibei.system.domain.bo.CityRegionQueryBo;
import com.yibei.system.domain.vo.CityRegionVo;
import com.yibei.system.mapper.CityRegionMapper;
import com.yibei.system.service.ICityRegionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author frame
 * @date 2021-07-14
 */
@Service
public class CityRegionServiceImpl extends ServiceImpl<CityRegionMapper, CityRegion> implements ICityRegionService {

    @Override
    public CityRegionVo queryById(Integer id){
        return getVoById(id, CityRegionVo.class);
    }

    @Override
    public TableDataInfo<CityRegionVo> queryPageList(CityRegionQueryBo bo) {
        PagePlus<CityRegion, CityRegionVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), CityRegionVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<CityRegionVo> queryList(CityRegionQueryBo bo) {
        return listVo(buildQueryWrapper(bo), CityRegionVo.class);
    }

    private LambdaQueryWrapper<CityRegion> buildQueryWrapper(CityRegionQueryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CityRegion> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCityId() != null, CityRegion::getCityId, bo.getCityId());
        lqw.like(StrUtil.isNotBlank(bo.getName()), CityRegion::getName, bo.getName());
        lqw.eq(bo.getSort() != null, CityRegion::getSort, bo.getSort());
        lqw.eq(bo.getState() != null, CityRegion::getState, bo.getState());
        lqw.eq(bo.getIsDeleted() != null, CityRegion::getIsDeleted, bo.getIsDeleted());
        return lqw;
    }

    @Override
    public Boolean insertByAddBo(CityRegionAddBo bo) {
        CityRegion add = BeanUtil.toBean(bo, CityRegion.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByEditBo(CityRegionEditBo bo) {
        CityRegion update = BeanUtil.toBean(bo, CityRegion.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(CityRegion entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Integer> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }
}
