package com.yibei.system.service;

import cn.hutool.core.lang.Pair;
import com.yibei.system.domain.VerificationCode;
import com.yibei.system.domain.vo.VerificationCodeVo;
import com.yibei.system.domain.bo.VerificationCodeBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;


import java.util.Collection;
import java.util.List;

/**
 * 验证码记录Service接口
 *
 * @author yibei
 * @date 2021-10-24
 */
public interface IVerificationCodeService extends IServicePlus<VerificationCode, VerificationCodeVo> {
	/**
	 * 发送验证码
	 * @param account 账号
	 * @param type 短信类型 登录、注册。。。
	 * @return
	 */
	Pair<Boolean,String> send(String account, int type);

	/**
	 * 对比验证码是否正确
	 * @return
	 */
	boolean compare(int accountType,String account,int type,String code,String uuid);

	/**
	 * 发送短信
	 * @param phone 手机号
	 * @return
	 */
	Pair<Boolean,String> sendSms(String phone ,String code);

	boolean sendMail(String to,String subject,String content);

	Pair<Boolean,String> compareAndRemove(String account, Integer type, String code);
}
