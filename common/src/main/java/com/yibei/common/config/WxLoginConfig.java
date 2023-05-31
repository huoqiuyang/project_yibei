package com.yibei.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("wxlogin")
public class WxLoginConfig {
    private String appId;
    private String appSecret;
}
