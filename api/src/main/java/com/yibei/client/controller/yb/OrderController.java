package com.yibei.client.controller.yb;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.CodesUtil;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.utils.TimeUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.yb.domain.*;
import com.yibei.yb.domain.clientBo.BuyVipBo;
import com.yibei.yb.domain.clientBo.CodeExchangeBo;
import com.yibei.yb.domain.clientVo.YbOrderInfoVo;
import com.yibei.yb.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController("Client_OrderController")
@Api(value = "订单模块",tags = "订单模块")
@RequestMapping("/client/order")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderController extends BaseController {

    private final IYbVipCodeService iYbVipCodeService;

    private final IYbUserVipService iYbUserVipService;

    private final IYbVipCommodityService iYbVipCommodityService;

    private final IYbOrderService iYbOrderService;

    private final IYbCodeExchangeService iYbCodeExchangeService;

    @ApiOperation("兑换码兑换会员")
    @PostMapping("/codeExchange")
    @LoginChecked
    @Transactional
    public AjaxResult codeExchange(@RequestBody CodeExchangeBo bo) {

        long userId = getUserId();

        YbVipCode vipCode = iYbVipCodeService.getOne(new LambdaQueryWrapper<YbVipCode>().eq(YbVipCode::getCode,bo.getCode()).eq(YbVipCode::getStatus,0).last("LIMIT 1"));
        if(vipCode == null){
            return AjaxResult.error("兑换码无效");
        }

        //兑换码限制只能使用一次 --20220913
        long vipNum = iYbCodeExchangeService.count(new LambdaQueryWrapper<YbCodeExchange>().eq(YbCodeExchange::getCodeId,vipCode.getId()));
        if(vipNum > 0){
            return AjaxResult.error("兑换码已使用");
        }

        /*long userVipNum = iYbCodeExchangeService.count(new LambdaQueryWrapper<YbCodeExchange>().eq(YbCodeExchange::getCodeId,vipCode.getId()).eq(YbCodeExchange::getUserId,userId));
        if(userVipNum > 0){
            return AjaxResult.error("兑换码已使用");
        }*/

        //更新用户会员信息
        YbUserVip userVip = iYbUserVipService.getOne(new LambdaQueryWrapper<YbUserVip>().eq(YbUserVip::getUserId,userId).last("LIMIT 1"));
        Date startTime = TimeUtils.getCurrentDate();
        if(userVip!=null){
            userVip.setStartTime(startTime);
            if(TimeUtils.timeCompare(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"),TimeUtils.formatLongDateToString(userVip.getEndTime()),"yyyy-MM-dd HH:mm:ss")){
                userVip.setEndTime(TimeUtils.getModifyDate(userVip.getEndTime(),vipCode.getVipDay()));
            }else{
                userVip.setEndTime(TimeUtils.getModifyDate(startTime,vipCode.getVipDay()));
            }

            if(!iYbUserVipService.updateById(userVip)){
                return AjaxResult.error("兑换失败");
            }
        }else{
            userVip = new YbUserVip();
            userVip.setUserId(userId);
            userVip.setStartTime(startTime);
            userVip.setEndTime(TimeUtils.getModifyDate(startTime,vipCode.getVipDay()));

            if(!iYbUserVipService.save(userVip)){
                return AjaxResult.error("兑换失败");
            }
        }

        //添加兑换码兑换记录
        YbCodeExchange codeExchange = new YbCodeExchange();
        codeExchange.setUserId(userId);
        codeExchange.setCodeId(vipCode.getId());
        codeExchange.setCode(vipCode.getCode());
        codeExchange.setVipDay(vipCode.getVipDay());
        iYbCodeExchangeService.save(codeExchange);

        return AjaxResult.success("兑换成功");
    }


    @ApiOperation("会员购买")
    @PostMapping("/buyVip")
    @LoginChecked
    public AjaxResult buyVip(@RequestBody BuyVipBo bo) {

        YbVipCommodity vipCommodity = iYbVipCommodityService.getById(bo.getVipCommodityId());
        if(vipCommodity == null){
            return AjaxResult.error("未找到相关会员");
        }

        YbOrder order = new YbOrder();
        order.setUserId(getUserId());
        order.setOrderNo(CodesUtil.getOrderCode(getUserId().intValue()));
        order.setOrderAmount(vipCommodity.getPrice());
        order.setStatus(0);
        order.setVipTitle(vipCommodity.getTitle());
        order.setVipMonth(vipCommodity.getMonth());
        order.setVipId(vipCommodity.getId());
        order.setRemarks(bo.getRemarks());
        iYbOrderService.save(order);
        return AjaxResult.success("订单编号",order.getOrderNo());
    }

    @ApiOperation("交易记录列表")
    @PostMapping("/orderRecordList")
    @LoginChecked
    public TableDataInfo<YbOrderInfoVo> orderRecordList(@RequestBody PageBo bo) {
        Page<YbOrderInfoVo> page = iYbOrderService.orderRecordList(bo,getUserId());
        return PageUtils.buildDataInfo(page.getRecords(),page.getTotal());
    }

    /**
     * 订单失效
     * （暂时不用，当前需求没有将待支付订单后续增加支付的功能）
     * */
    /*@Scheduled(cron = "0 0/1 * * * ?")
    public void orderOverdue(){
        int minute = 10;//失效分钟数
        String time = TimeUtils.formatIntToDateString(TimeUtils.getCurrentTime()-60*minute);
        LambdaQueryWrapper<YbOrder> lqw = new LambdaQueryWrapper<>();
        lqw.eq(YbOrder::getStatus,0);
        lqw.lt(YbOrder::getCreateTime,time);
        List<YbOrder> list = iYbOrderService.list(lqw);
        if(list!=null && list.size()>0){
            for(YbOrder order : list){
                order.setStatus(-1);
                iYbOrderService.updateById(order);
            }
        }
    }*/

}
