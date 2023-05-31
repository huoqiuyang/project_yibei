package com.yibei.client.controller.yb;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.kit.HttpKit;
import com.ijpay.core.kit.WxPayKit;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.DateUtils;
import com.yibei.common.utils.TimeUtils;
import com.yibei.yb.domain.YbOrder;
import com.yibei.yb.domain.YbOrderPay;
import com.yibei.yb.domain.YbUserVip;
import com.yibei.yb.domain.YbVipCode;
import com.yibei.yb.service.IYbOrderPayService;
import com.yibei.yb.service.IYbOrderService;
import com.yibei.yb.service.IYbUserVipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController("Client_PayNotifyController")
@Api(value = "支付回调模块",tags = "支付回调模块")
@RequestMapping("/client/payNotify")
public class PayNotifyController extends BaseController {


    @Autowired
    private IYbOrderService iYbOrderService;

    @Autowired
    private IYbOrderPayService iYbOrderPayService;

    @Autowired
    private IYbUserVipService iYbUserVipService;

    @Value("${wxpay.partnerKey}")
    private String wxKey;

    @ApiOperation("微信回调")
    @PostMapping("/weChatNotify")
    @ResponseBody
    public String weChatNotify(HttpServletRequest request){
        try {
            // 获取支付宝POST过来反馈信息
            String xmlMsg = HttpKit.readData(request);
            Map<String, String> params = WxPayKit.xmlToMap(xmlMsg);

            String returnCode = params.get("return_code");
            String orderCode = params.get("out_trade_no");
            String tradeNo = params.get("transaction_id");
            String totalFee = params.get("total_fee");
            String payTime = timeFormat(params.get("time_end"));
            BigDecimal payFee = new BigDecimal(totalFee);
            //分转元
            payFee = payFee.divide(BigDecimal.valueOf(100));

            YbOrderPay ybOrderPay = new YbOrderPay();

            //验证
            if (WxPayKit.verifyNotify(params, wxKey, SignType.HMACSHA256)) {
                if ("SUCCESS".equals(returnCode)) {
                    //截取订单编号
                    orderCode = orderCode.substring(0,15);
                    //更新订单交易状态
                    Long userId = updateOrder(orderCode);
                    ybOrderPay.setUserId(userId);
                }

                //查询订单是否已经记录到数据库
                long count = iYbOrderPayService.count(new LambdaQueryWrapper<YbOrderPay>().eq(YbOrderPay::getOrderNo, orderCode));
                if(count < 1){
                    //增加支付记录
                    ybOrderPay.setOrderNo(orderCode);
                    ybOrderPay.setPayTime(DateUtils.parseDate(payTime));
                    ybOrderPay.setPayAmount(payFee);
                    ybOrderPay.setTradeNo(tradeNo);
                    ybOrderPay.setResponseData(params.toString());
                    iYbOrderPayService.save(ybOrderPay);
                }
                // 发送通知等
                Map<String, String> xml = new HashMap(2);
                xml.put("return_code", "SUCCESS");
                xml.put("return_msg", "OK");
                return WxPayKit.toXml(xml);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 更新订单交易状态
     * orderCode:订单编号
     * @return : 用户id
     * */
    @Transactional(rollbackFor = Exception.class)
    public Long updateOrder(String orderCode){

        Long userId = 0l;

        //订单信息
        YbOrder order = iYbOrderService.getOne(new LambdaQueryWrapper<YbOrder>().eq(YbOrder::getOrderNo,orderCode).eq(YbOrder::getStatus,0).last("LIMIT 1"));
        if(order!=null){
            order.setStatus(1);
            if(iYbOrderService.updateById(order)){
                userId = order.getUserId();

                //更新用户会员信息
                int day = order.getVipMonth()*30;

                YbUserVip userVip = iYbUserVipService.getOne(new LambdaQueryWrapper<YbUserVip>().eq(YbUserVip::getUserId,userId).last("LIMIT 1"));
                Date startTime = TimeUtils.getCurrentDate();
                if(userVip!=null){
                    userVip.setStartTime(startTime);
                    if(TimeUtils.timeCompare(TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"),TimeUtils.formatLongDateToString(userVip.getEndTime()),"yyyy-MM-dd HH:mm:ss")){
                        userVip.setEndTime(TimeUtils.getModifyDate(userVip.getEndTime(),day));
                    }else{
                        userVip.setEndTime(TimeUtils.getModifyDate(startTime,day));
                    }
                    iYbUserVipService.updateById(userVip);
                }else{
                    userVip = new YbUserVip();
                    userVip.setUserId(userId);
                    userVip.setStartTime(startTime);
                    userVip.setEndTime(TimeUtils.getModifyDate(startTime,day));
                    iYbUserVipService.save(userVip);
                }

            }
        }

        return userId;
    }

    /**
     * 转换时间格式
     */
    public static String timeFormat(String timestamp){
        String yy = timestamp.substring(0, 4);
        String MM = timestamp.substring(4, 6);
        String dd = timestamp.substring(6, 8);
        String hh = timestamp.substring(8, 10);
        String mm = timestamp.substring(10, 12);
        String ss = timestamp.substring(12, 14);
        return yy + "-" + MM + "-" + dd + " " + hh + ":" + mm + ":" + ss;
    }

}
