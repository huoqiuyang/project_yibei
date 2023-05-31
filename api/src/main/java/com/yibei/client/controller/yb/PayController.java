package com.yibei.client.controller.yb;

import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.client.controller.BaseController;
import com.yibei.common.config.WxPayConfig;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.PayUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.yb.domain.YbOrder;
import com.yibei.yb.domain.bo.OrderNoBo;
import com.yibei.yb.service.IYbOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.hutool.core.lang.Pair;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;


@RestController("Client_PayController")
@Api(value = "支付模块",tags = "支付模块")
@RequestMapping("/client/pay")
public class PayController extends BaseController {

    @Autowired
    private WxPayConfig wxPayConfig;

    @Autowired
    private IYbOrderService iYbOrderService;


    @ApiOperation("订单支付")
    @PostMapping("/orderPay")
    public AjaxResult orderPay(@RequestBody @Validated OrderNoBo bo){

        String openid = null;
        try {
            if(StringUtils.isBlank(bo.getCode())){
                return AjaxResult.error("code不能为空");
            }
            //小程序获取unionid
            String URL = "https://api.weixin.qq.com/sns/jscode2session?appid="+ wxPayConfig.getAppId() +"&secret="+ wxPayConfig.getAppSecret() +"&js_code="+ bo.getCode() +"&grant_type=authorization_code";
            String result = HttpRequest.get(URL)
                    .timeout(20000)
                    .execute().body();
            JSONObject jsonObject = JSONObject.fromObject(result);
            if(jsonObject.containsKey("openid")){
                openid = jsonObject.getString("openid");
            }
            if(openid == null){
                return AjaxResult.error("获取微信信息失败");
            }
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("操作失败");
        }

        YbOrder order = iYbOrderService.getOne(new LambdaQueryWrapper<YbOrder>().eq(YbOrder::getOrderNo, bo.getOrderNo()), false);
        if(order == null){
            return AjaxResult.error("未查到相关订单");
        }
        if(order.getStatus() == 0){
            //支付金额
            BigDecimal totalAmount = order.getOrderAmount();
            //测试默认0.01元
//            totalAmount = BigDecimal.valueOf(0.01);

            Pair<Boolean,String> pair = PayUtils.wxPayAppPay(wxPayConfig
                    ,openid
                    ,"订单下单"
                    , order.getUserId().toString()
                    ,order.getOrderNo() + order.getPayCount()
                    , totalAmount
                    ,wxPayConfig.getNotifyUrl() + "/payNotify/weChatNotify");

            order.setPayCount(order.getPayCount()+1);
            iYbOrderService.updateById(order);

            return AjaxResult.success(pair);
        }else{
            return AjaxResult.error("订单状态不是待支付");
        }
    }



}
