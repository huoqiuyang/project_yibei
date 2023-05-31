package com.yibei.common.utils.ip;

import cn.hutool.core.net.NetUtil;
import cn.hutool.http.HtmlUtil;
import cn.hutool.http.HttpUtil;
import com.yibei.common.config.SiteConfig;
import com.yibei.common.constant.Constants;
import com.yibei.common.utils.JsonUtils;
import com.yibei.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 获取地址类
 *
 * @author
 */
@Slf4j
public class AddressUtils {

	// IP地址查询
	public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

	// 未知地址
	public static final String UNKNOWN = "-";

	public static String getRealAddressByIP(String ip) {
		String address = UNKNOWN;
		// 内网不查询
		ip = "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : HtmlUtil.cleanHtmlTag(ip);
		if (NetUtil.isInnerIP(ip)) {
			return "内网IP";
		}
		if (SiteConfig.isAddressEnabled()) {
			try {
				String rspStr = HttpUtil.createGet(IP_URL)
					.body("ip=" + ip + "&json=true", Constants.GBK)
					.execute()
					.body();
				if (StringUtils.isEmpty(rspStr)) {
					log.error("获取地理位置异常 {}", ip);
					return UNKNOWN;
				}
				Map<String, String> obj = JsonUtils.parseMap(rspStr);
				String region = obj.get("pro");
				String city = obj.get("city");
				return String.format("%s %s", region, city);
			} catch (Exception e) {
				log.error("获取地理位置异常 {}", ip);
			}
		}
		return address;
	}
}
