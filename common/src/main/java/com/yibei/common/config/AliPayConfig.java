package com.yibei.common.config;

import com.alipay.api.CertAlipayRequest;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("alipay")
public class AliPayConfig {

    private String appId;
    private String privateKey;
    private String publicKeyCert;
    private String alipayPublicKeyCert;
    private String alipayRootCert;

    private String notifyUrl;

    public CertAlipayRequest getInstance(){
        CertAlipayRequest certAlipayRequest = new CertAlipayRequest();
        certAlipayRequest.setServerUrl("https://openapi.alipay.com/gateway.do");
        certAlipayRequest.setAppId(getAppId());
        certAlipayRequest.setPrivateKey(getPrivateKey());
        certAlipayRequest.setFormat("json");
        certAlipayRequest.setCharset("UTF-8");
        certAlipayRequest.setSignType("RSA2");
        certAlipayRequest.setCertPath(getPublicKeyCert());
        certAlipayRequest.setAlipayPublicCertPath(getAlipayPublicKeyCert());
        certAlipayRequest.setRootCertPath(getAlipayRootCert());
        return certAlipayRequest;
    }
}
