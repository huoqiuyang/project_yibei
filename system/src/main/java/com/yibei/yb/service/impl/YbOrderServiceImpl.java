package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.User;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.service.IUserService;
import com.yibei.yb.domain.YbOrderPay;
import com.yibei.yb.domain.YbVipCommodity;
import com.yibei.yb.domain.clientVo.YbOrderInfoVo;
import com.yibei.yb.domain.vo.YbOrderPayVo;
import com.yibei.yb.domain.vo.YbVipCommodityVo;
import com.yibei.yb.service.IYbOrderPayService;
import com.yibei.yb.service.IYbVipCommodityService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbOrderBo;
import com.yibei.yb.domain.vo.YbOrderVo;
import com.yibei.yb.domain.YbOrder;
import com.yibei.yb.mapper.YbOrderMapper;
import com.yibei.yb.service.IYbOrderService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 会员订单Service业务层处理
 *
 * @author yibei
 * @date 2022-04-27
 */
@Service
public class YbOrderServiceImpl extends ServicePlusImpl<YbOrderMapper, YbOrder, YbOrderVo> implements IYbOrderService {

    @Resource
    private IUserService iUserService;
    @Resource
    private IYbVipCommodityService iYbVipCommodityService;
    @Resource
    private IYbOrderPayService iYbOrderPayService;

    @Override
    public YbOrderVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbOrderVo> queryPageList(YbOrderBo bo) {
        PagePlus<YbOrder, YbOrderVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbOrderVo> queryList(YbOrderBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbOrder> buildQueryWrapper(YbOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbOrder> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbOrder::getCreateTime);
//        lqw.orderByDesc(YbOrder::getId);
        lqw.eq(bo.getUserId() != null, YbOrder::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getOrderNo()), YbOrder::getOrderNo, bo.getOrderNo());
        lqw.eq(bo.getOrderAmount() != null, YbOrder::getOrderAmount, bo.getOrderAmount());
        lqw.eq(bo.getStatus() != null, YbOrder::getStatus, bo.getStatus());
        lqw.like(StringUtils.isNotBlank(bo.getVipTitle()), YbOrder::getVipTitle, bo.getVipTitle());
        lqw.eq(bo.getVipMonth() != null, YbOrder::getVipMonth, bo.getVipMonth());
        lqw.eq(bo.getVipId() != null, YbOrder::getVipId, bo.getVipId());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), YbOrder::getRemarks, bo.getRemarks());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbOrderBo bo) {
        YbOrder add = BeanUtil.toBean(bo, YbOrder.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbOrderBo bo) {
        YbOrder update = BeanUtil.toBean(bo, YbOrder.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbOrder entity){
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
    public YbOrderVo queryExtData(YbOrderVo vo) {
        List<YbOrderVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbOrderVo> queryExtData(List<YbOrderVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String userIds = list.stream().map(s -> s.getUserId().toString()).collect(Collectors.joining(","));
        userIds = StringUtils.isBlank(userIds)?"0":userIds;
        List<UserVo> userVoList = iUserService.listVo(new LambdaQueryWrapper<User>().inSql(User::getId, userIds));
        list.forEach(item->{
        item.getExtData().put("user", userVoList.stream().filter(s -> s.getId().compareTo(item.getUserId()) == 0).findFirst().orElse(null));
        });
        String vipIds = list.stream().map(s -> s.getVipId().toString()).collect(Collectors.joining(","));
        vipIds = StringUtils.isBlank(vipIds)?"0":vipIds;
        List<YbVipCommodityVo> ybVipCommodityVoList = iYbVipCommodityService.listVo(new LambdaQueryWrapper<YbVipCommodity>().inSql(YbVipCommodity::getId, vipIds));
        list.forEach(item->{
        item.getExtData().put("ybVipCommodity", ybVipCommodityVoList.stream().filter(s -> s.getId().compareTo(item.getVipId()) == 0).findFirst().orElse(null));
        });
        String orderNos = list.stream().map(s -> s.getOrderNo().toString()).collect(Collectors.joining(","));
        orderNos = StringUtils.isBlank(orderNos)?"0":orderNos;
        List<YbOrderPayVo> ybOrderPayVoList = iYbOrderPayService.listVo(new LambdaQueryWrapper<YbOrderPay>().inSql(YbOrderPay::getOrderNo, orderNos).orderByDesc(YbOrderPay::getCreateTime));
        list.forEach(item->{
            item.getExtData().put("ybOrderPay", ybOrderPayVoList.stream().filter(s -> s.getOrderNo().compareTo(item.getOrderNo()) == 0).findFirst().orElse(null));
        });

        return list;
    }

    @Override
    public Page<YbOrderInfoVo> orderRecordList(PageBo bo, Long userId) {
        return baseMapper.orderRecordList(new Page(bo.getPageNum(),bo.getPageSize()),userId);
    }
}
