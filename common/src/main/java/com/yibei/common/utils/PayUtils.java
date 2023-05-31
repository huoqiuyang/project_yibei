package com.yibei.common.utils;

import com.alibaba.fastjson.JSON;
import com.ijpay.core.enums.SignType;
import com.ijpay.core.enums.TradeType;
import com.ijpay.core.kit.WxPayKit;
import com.ijpay.wxpay.WxPayApi;
import com.ijpay.wxpay.WxPayApiConfig;
import com.ijpay.wxpay.WxPayApiConfigKit;
import com.ijpay.wxpay.model.RefundModel;
import com.ijpay.wxpay.model.UnifiedOrderModel;
import com.yibei.common.config.WxPayConfig;
import cn.hutool.core.lang.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class PayUtils {

    private static String webUrl;

    @Value("${webUrl}")
    public void  setWebUrl(String webUrl){
        PayUtils.webUrl = webUrl;
    }

    private static final Logger log = LoggerFactory.getLogger(PayUtils.class);

    public static WxPayApiConfig getApiConfigByWxAppPay(WxPayConfig wxPayConfig){
        WxPayApiConfig apiConfig;
        try {
            apiConfig = WxPayApiConfigKit.getApiConfig(wxPayConfig.getAppId());
        } catch (Exception e) {
            apiConfig = WxPayApiConfig.builder()
                    .appId(wxPayConfig.getAppId())
                    .mchId(wxPayConfig.getMchId())
                    .partnerKey(wxPayConfig.getPartnerKey())
                    .build();
            WxPayApiConfigKit.setThreadLocalWxPayApiConfig(apiConfig);
        }
        return apiConfig;
    }


    /**
     * 微信小程序支付
     * @param wxPayConfig
     * @param body
     * @param subject
     * @param outTradeNo
     * @param totalAmount
     * @param notifyUrl
     * @return
     */
    public static Pair<Boolean,String> wxPayAppPay(WxPayConfig wxPayConfig, String openId, String body, String subject, String outTradeNo, BigDecimal totalAmount,String notifyUrl){
        WxPayApiConfig apiConfig = PayUtils.getApiConfigByWxAppPay(wxPayConfig);
        if (apiConfig==null){
            return new Pair<>(false,"微信支付初始化错误");
        }

        Map<String, String> params = UnifiedOrderModel
                .builder()
                .appid(apiConfig.getAppId())
                .mch_id(apiConfig.getMchId())
                .nonce_str(WxPayKit.generateStr())
                .body(body)
                .attach(subject)
                .out_trade_no(outTradeNo)
                .total_fee(String.valueOf(totalAmount.multiply(BigDecimal.valueOf(100)).intValue()))
                .spbill_create_ip("127.0.0.1")
                .notify_url(notifyUrl)
                .trade_type(TradeType.JSAPI.getTradeType())
                .openid(openId)
                .build()
                .createSign(apiConfig.getPartnerKey(), SignType.HMACSHA256);

        String xmlResult = WxPayApi.pushOrder(false, params);

        Map<String, String> result = WxPayKit.xmlToMap(xmlResult);

        String returnCode = result.get("return_code");
        String returnMsg = result.get("return_msg");
        if (!WxPayKit.codeIsOk(returnCode)) {
            return new Pair<>(false,returnMsg);
        }
        String resultCode = result.get("result_code");
        if (!WxPayKit.codeIsOk(resultCode)) {
            return new Pair<>(false,resultCode);
        }
        // 以下字段在 return_code 和 result_code 都为 SUCCESS 的时候有返回
        String prepayId = result.get("prepay_id");

        Map<String, String> packageParams = WxPayKit.prepayIdCreateSign(prepayId, wxPayConfig.getAppId(),
                wxPayConfig.getPartnerKey(), SignType.HMACSHA256);
        String jsonStr = JSON.toJSONString(packageParams);

        return new Pair<>(true,jsonStr);
    }

    public static Pair<Boolean,Object> wxPayRefund(WxPayConfig wxPayConfig,String outTradeNo,String outRefundNo, BigDecimal totalAmount,String notifyUrl){
        WxPayApiConfig apiConfig = PayUtils.getApiConfigByWxAppPay(wxPayConfig);
        if (apiConfig==null){
            return new Pair<>(false,"微信支付初始化错误");
        }
        Map<String, String> params = RefundModel.builder()
            .appid(apiConfig.getAppId())
            .mch_id(apiConfig.getMchId())
            .nonce_str(WxPayKit.generateStr())
            //.transaction_id(transactionId)
            .out_trade_no(outTradeNo)
            .out_refund_no(outRefundNo)
            .total_fee(String.valueOf(totalAmount.multiply(BigDecimal.valueOf(100)).intValue()))
            .refund_fee(String.valueOf(totalAmount.multiply(BigDecimal.valueOf(100)).intValue()))
            .notify_url(notifyUrl)
            .build()
            .createSign(wxPayConfig.getPartnerKey(), SignType.HMACSHA256);
        String ret = WxPayApi.orderRefund(false, params, wxPayConfig.getCertPath(), wxPayConfig.getMchId());
        Map<String,String> map = WxPayKit.xmlToMap(ret);
        log.error("微信退款记录", map);
        return new Pair<>(map.get("result_code").equals("SUCCESS"),map);
    }


}
