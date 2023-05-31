package com.yibei.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.RedisUtils;
import com.yibei.system.domain.User;
import com.yibei.system.domain.clientBo.VerificationCodeCompareBo;
import com.yibei.system.domain.clientBo.VerificationCodeSendBo;
import com.yibei.system.service.IUserService;
import com.yibei.system.service.IVerificationCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import cn.hutool.core.lang.Pair;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.TimeUnit;


//@Api(tags = "验证码模块")
//@RestController("Client_VerificationCodeController")
//@RequestMapping("/client/vrificationCode")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VerificationCodeController extends BaseController {

//    private final IVerificationCodeService iVerificationCodeService;
//    private final IUserService iUserService;
//
//    @ApiOperation("发送验证码")
//    @PostMapping("/send")
//    public AjaxResult send(@RequestBody @Valid VerificationCodeSendBo bo)
//    {
//
//        Integer errCount = RedisUtils.getCacheObject("PhoneCodeErrCount:" + bo.getAccount());
//        if(errCount != null){
//            return AjaxResult.error("验证码连续输入错误次数过多，已封停" + errCount + "分钟");
//        }
//
//        if(bo.getType().equals(1)){
//            long count = iUserService.count(new LambdaQueryWrapper<User>().eq(User::getPhone, bo.getAccount()));
//            if(count > 0){
//                return AjaxResult.error("该手机号已注册");
//            }
//        }
//
//        Pair<Boolean,String> pair = iVerificationCodeService.send(bo.getAccount(),bo.getType());
//        if (!pair.getKey()){
//            return AjaxResult.error(pair.getValue(),null);
//        }
//        return AjaxResult.success("发送成功");
//    }
//
//    @ApiOperation("比对验证码")
//    @PostMapping("/compare")
//    public AjaxResult compare(@RequestBody @Valid VerificationCodeCompareBo vo)
//    {
//        Pair<Boolean,String> pair = iVerificationCodeService.compareAndRemove(vo.getAccount(),vo.getType(),vo.getCode());
//        if (!pair.getKey()){
//            return AjaxResult.error(pair.getValue());
//        }
//        return AjaxResult.success();
//    }


}
