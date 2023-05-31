package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.yb.domain.YbCodeExchange;
import com.yibei.yb.service.IYbCodeExchangeService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbVipCodeBo;
import com.yibei.yb.domain.vo.YbVipCodeVo;
import com.yibei.yb.domain.YbVipCode;
import com.yibei.yb.mapper.YbVipCodeMapper;
import com.yibei.yb.service.IYbVipCodeService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 会员兑换码Service业务层处理
 *
 * @author yibei
 * @date 2022-04-27
 */
@Service
public class YbVipCodeServiceImpl extends ServicePlusImpl<YbVipCodeMapper, YbVipCode, YbVipCodeVo> implements IYbVipCodeService {

    @Resource
    private IYbCodeExchangeService iYbCodeExchangeService;

    @Override
    public YbVipCodeVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbVipCodeVo> queryPageList(YbVipCodeBo bo) {
        PagePlus<YbVipCode, YbVipCodeVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbVipCodeVo> queryList(YbVipCodeBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbVipCode> buildQueryWrapper(YbVipCodeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbVipCode> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbVipCode::getCreateTime);
//        lqw.orderByDesc(YbVipCode::getId);
        lqw.like(StringUtils.isNotBlank(bo.getCode()), YbVipCode::getCode, bo.getCode());
        lqw.eq(bo.getVipDay()!=null, YbVipCode::getVipDay, bo.getVipDay());
        lqw.eq(bo.getStatus() != null, YbVipCode::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbVipCodeBo bo) {
        YbVipCode add = BeanUtil.toBean(bo, YbVipCode.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbVipCodeBo bo) {
        YbVipCode update = BeanUtil.toBean(bo, YbVipCode.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbVipCode entity){
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
    public YbVipCodeVo queryExtData(YbVipCodeVo vo) {
        List<YbVipCodeVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbVipCodeVo> queryExtData(List<YbVipCodeVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        list.forEach(item->{
            item.getExtData().put("exchangeNum", iYbCodeExchangeService.count(new LambdaQueryWrapper<YbCodeExchange>().eq(YbCodeExchange::getCodeId, item.getId())));
        });

        return list;
    }
}
