package com.yibei.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Slf4j
@Component
public class WxUtils {

	private static RestTemplate restTemplate;


	/**
	 * 获取微信用户信息
	 * @return
	 */
	public static JSONObject getUserInfo(String accessToken,String openid){
		// 设置restemplate编码为utf-8
		restTemplate.getMessageConverters().set(1,new StringHttpMessageConverter(StandardCharsets.UTF_8));

		String URL = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openid;
		String result = restTemplate.getForObject(URL, String.class);
		JSONObject jsonObject = JSON.parseObject(result);
		if (jsonObject.containsKey("errcode")){
			return null;
		}
		return jsonObject;
	}


	/**
	 * 微信小程序解密
	 * @param encryptedData
	 * @param sessionKey
	 * @param iv
	 * @return
	 */
	public static net.sf.json.JSONObject getUserDecodeInfo(String encryptedData, String sessionKey, String iv) {
		net.sf.json.JSONObject result = null;
		// 被加密的数据
		byte[] dataByte = Base64.decodeBase64(encryptedData);
		// 加密秘钥
		byte[] keyByte = Base64.decodeBase64(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.decodeBase64(iv);
		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base
					+ (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters
				.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			// 初始化
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				result = net.sf.json.JSONObject.fromObject(new String(resultByte, "UTF-8"));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidParameterSpecException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String generateSignature(Map<String, String> data, String key) throws Exception {
		SortedMap<String, String> map = new TreeMap<>();
		map.put("appid", data.get("appid"));
		map.put("mch_id", data.get("mch_id"));
		map.put("body", data.get("body"));
		map.put("out_trade_no", data.get("out_trade_no"));
		map.put("trade_type", "JSAPI");
		if(data.containsKey("openid")){
			map.put("openid", data.get("openid"));
		}
		map.put("attach", data.get("attach"));
		if(data.containsKey("cash_fee")){
			map.put("total_fee", data.get("cash_fee"));
		}else{
			map.put("total_fee", data.get("total_fee"));
		}
		String sign = WXPayUtil.generateSignature(data, key);
		log.info("map：" + map + " ;key：" + key);
		log.info("sign：" + sign);
		return sign;
	}

}
