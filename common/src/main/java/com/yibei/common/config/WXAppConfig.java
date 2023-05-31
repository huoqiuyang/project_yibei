package com.yibei.common.config;


import com.github.wxpay.sdk.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class WXAppConfig implements WXPayConfig {

	private static WxPayConfig config;

    private byte[] certData;

	public WXAppConfig(WxPayConfig wxpayConfig) {
		config = wxpayConfig;
	}

	@Override
	public String getAppID() {
		return config.getAppId();
	}

	@Override
	public String getMchID() {
		return config.getMchId();
	}

	@Override
	public String getKey() {
		return config.getPartnerKey();
	}

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }
}
