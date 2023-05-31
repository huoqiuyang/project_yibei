package com.yibei.common.utils;

import com.yibei.common.config.WxPayConfig;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;

@Component
public class WeChatApiUtil {


	private static WxPayConfig wxPayConfig;

	@Autowired
	public  void setJpushApiConfig(WxPayConfig wxPayConfig) {
		WeChatApiUtil.wxPayConfig = wxPayConfig;
	}

	/**
	 * 获取微信session_key
	 * @param code
	 */
	public static String getSessionKey(String code) {
		String URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxPayConfig.getAppId() + "&secret="
				+ wxPayConfig.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if(jsonObject != null && jsonObject.containsKey("session_key")){
			return jsonObject.getString("session_key");
		}else{
			return null;
		}
	}
	
	/**
	 * 获取微信openid
	 * @param code
	 */
	public static String getOpenid(String code) {
		String URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxPayConfig.getAppId() + "&secret="
				+ wxPayConfig.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if(jsonObject != null && jsonObject.containsKey("openid")){
			return jsonObject.getString("openid");
		}else{
			return null;
		}
	}
	
	/**
	 * 获取微信session_key,openid,unionid
	 * @param code
	 */
	public static String getUnionid(String code) {
		String URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxPayConfig.getAppId() + "&secret="
				+ wxPayConfig.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if(jsonObject != null && jsonObject.containsKey("unionid")){
			return jsonObject.getString("unionid");
		}else{
			return null;
		}
	}
	
	/**
	 * 获取微信unionid
	 * @param code
	 */
	public static JSONObject getOppid(String code) {
		String URL = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxPayConfig.getAppId() + "&secret="
				+ wxPayConfig.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if(jsonObject != null && jsonObject.containsKey("openid")){
			return jsonObject;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取微信access_token
	 */
	public static String getAccessToken() {
		String URL = "https://api.weixin.qq.com/cgi-bin/token?appid="+ wxPayConfig.getAppId() +"&secret="+ wxPayConfig.getAppSecret() +"&grant_type=client_credential";
		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if(jsonObject != null && jsonObject.containsKey("access_token")){
			return jsonObject.getString("access_token");
		}else{
			return null;
		}
	}
	
    /**
     * 微信小程序解密
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
    	JSONObject result = null;
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
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                result = JSONObject.fromObject(new String(resultByte, "UTF-8"));
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
}
