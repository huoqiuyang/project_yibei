package com.yibei.system.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.yibei.common.utils.AliyunSmsUtils;
import com.yibei.common.utils.RedisUtils;
import com.yibei.system.domain.VerificationCode;
import com.yibei.system.domain.vo.VerificationCodeVo;
import com.yibei.system.mapper.VerificationCodeMapper;
import com.yibei.system.service.IVerificationCodeService;
import cn.hutool.core.lang.Pair;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 验证码记录Service业务层处理
 *
 * @author yibei
 * @date 2021-10-24
 */
@Service
public class VerificationCodeServiceImpl extends ServicePlusImpl<VerificationCodeMapper, VerificationCode, VerificationCodeVo> implements IVerificationCodeService {


    @Value("${sms.aliyun.accessKeyId}")
    private String smsAccessKeyId;
    @Value("${sms.aliyun.accessKeySecret}")
    private String smsAccessKeySecret;
    @Value("${sms.aliyun.signName}")
    private String smsSignName;
    @Value("${sms.aliyun.templateCode}")
    private String smsTemplateCode;

    @Autowired
    private IVerificationCodeService iVerificationCodeService;

    @Override
    public Pair<Boolean,String> send(String account, int type) {

        //生成验证码
        String code = RandomStringUtils.randomNumeric(6);

        //发送成功 写入数据库
        VerificationCode vcode = new VerificationCode();
        vcode.setAccount(account);
        vcode.setType(type);
        vcode.setCode(code);
        vcode.setStatus(0);
        vcode.setInvalidTime(LocalDateTime.now().plusMinutes(10));//10分钟过期
        if (!save(vcode)) {
            return new Pair<>(false, "验证码发送失败");
        }

        boolean flag = false;
        //发送短信
        flag = sendSms(account,code).getKey();

        if (!flag){
            return new Pair<>(false,"验证码发送失败");
        }

        return new Pair<>(true, code);
    }

    @Override
    public boolean compare(int accountType, String account, int type, String code,String uuid) {
        //从redis取验证码

        return true;
    }

    @Override
    public Pair<Boolean,String> sendSms(String phone ,String code) {
        Client client = null;
        try {
            client = AliyunSmsUtils.createClient(smsAccessKeyId, smsAccessKeySecret);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers(phone)
                    .setTemplateCode(smsTemplateCode)
                    .setSignName(smsSignName)
                    .setTemplateParam("{\"code\":\""+code+"\"}");
            SendSmsResponse sendSmsResponse = client.sendSms( sendSmsRequest );
            if(!sendSmsResponse.body.getCode().equals( "OK" )){
                return new Pair<>(false,sendSmsResponse.body.getMessage());
            }
        } catch (Exception e) {
            return new Pair<>(false,"短信发送失败");
        }

        return new Pair<>(true,"短信发送成功");
    }

    @Override
    public boolean sendMail(String to,String subject, String content) {

//        MailAccount account = new MailAccount();
//		account.setFrom(emailFrom);
//		account.setPass(emailPass);
//		MailUtil.send(account, to, subject, content, false);
        return true;
    }

    @Override
    public Pair<Boolean,String> compareAndRemove(String account, Integer type, String code) {
        Integer errCount = RedisUtils.getCacheObject("PhoneCodeErrCount:" + account);
        if(errCount != null && errCount > 10){
            RedisUtils.setCacheObject("PhoneCodeErrCount:" + account,errCount + 1,errCount + 1, TimeUnit.MINUTES);
            return new Pair<>(false, "验证码连续输入错误次数过多，封停" + errCount + 1 + "分钟");
        }

        List<VerificationCode> verificationCodes = baseMapper.selectList(new LambdaQueryWrapper<VerificationCode>()
                .eq(VerificationCode::getAccount, account)
                .eq(VerificationCode::getType, type)
                .eq(VerificationCode::getIsDeleted, 0)
                .eq(VerificationCode::getStatus,0)
                .ge(VerificationCode::getInvalidTime,LocalDateTime.now())
                .eq(VerificationCode::getCode, code));
        if(verificationCodes.size()>0){
            for (VerificationCode s : verificationCodes ) {
                Long id = s.getId();
                s = new VerificationCode();
                s.setId(id);
                s.setStatus(1);
                baseMapper.updateById(s);
            }

            RedisUtils.deleteObject("PhoneCodeErrCount:" + account);

            return new Pair<>(true, "验证通过");
        }else{

            if(errCount == null){
                RedisUtils.setCacheObject("PhoneCodeErrCount:" + account,1,1, TimeUnit.MINUTES);
            }else{
                RedisUtils.setCacheObject("PhoneCodeErrCount:" + account,errCount + 1,5, TimeUnit.MINUTES);
            }
            return new Pair<>(false, "验证码错误");
        }
    }

    public static void main(String[] args) {
        Client client = null;
        try {
            client = AliyunSmsUtils.createClient("LTAI5tEwVftWMuH7jNP9vb9a", "mKRS0U8BKj7Dih5jjBifkWnDOPFGRc");
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setPhoneNumbers("18523428758")
                    .setTemplateCode("SMS_231600010")
                    .setSignName("一砖一瓦企业服务")
                    .setTemplateParam("{\"code\":\"321544\"}");
            SendSmsResponse sendSmsResponse = client.sendSms( sendSmsRequest );
            System.out.println(sendSmsResponse.body.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
