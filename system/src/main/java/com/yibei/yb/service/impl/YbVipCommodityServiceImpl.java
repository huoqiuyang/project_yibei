package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbVipCommodityBo;
import com.yibei.yb.domain.vo.YbVipCommodityVo;
import com.yibei.yb.domain.YbVipCommodity;
import com.yibei.yb.mapper.YbVipCommodityMapper;
import com.yibei.yb.service.IYbVipCommodityService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 会员中心Service业务层处理
 *
 * @author yibei
 * @date 2022-04-27
 */
@Service
public class YbVipCommodityServiceImpl extends ServicePlusImpl<YbVipCommodityMapper, YbVipCommodity, YbVipCommodityVo> implements IYbVipCommodityService {


    @Override
    public YbVipCommodityVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbVipCommodityVo> queryPageList(YbVipCommodityBo bo) {
        PagePlus<YbVipCommodity, YbVipCommodityVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbVipCommodityVo> queryList(YbVipCommodityBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbVipCommodity> buildQueryWrapper(YbVipCommodityBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbVipCommodity> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(YbVipCommodity::getSort);
        lqw.orderByDesc(YbVipCommodity::getCreateTime);
//        lqw.orderByDesc(YbVipCommodity::getId);
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), YbVipCommodity::getTitle, bo.getTitle());
        lqw.like(StringUtils.isNotBlank(bo.getLabel()), YbVipCommodity::getLabel, bo.getLabel());
        lqw.eq(bo.getMonth() != null, YbVipCommodity::getMonth, bo.getMonth());
        lqw.eq(bo.getPrice() != null, YbVipCommodity::getPrice, bo.getPrice());
        lqw.eq(bo.getPrecioMensual() != null, YbVipCommodity::getPrecioMensual, bo.getPrecioMensual());
        lqw.eq(bo.getSort() != null, YbVipCommodity::getSort, bo.getSort());
        lqw.eq(bo.getStatus() != null, YbVipCommodity::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbVipCommodityBo bo) {
        YbVipCommodity add = BeanUtil.toBean(bo, YbVipCommodity.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbVipCommodityBo bo) {
        YbVipCommodity update = BeanUtil.toBean(bo, YbVipCommodity.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbVipCommodity entity){
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
    public YbVipCommodityVo queryExtData(YbVipCommodityVo vo) {
        List<YbVipCommodityVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbVipCommodityVo> queryExtData(List<YbVipCommodityVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }
}
