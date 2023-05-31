package com.yibei.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("wxpay")
public class WxPayConfig {
    private String appId;
    private String appSecret;
    private String mchId;
    private String partnerKey;
    private String certPath;
    private String notifyUrl;

}
