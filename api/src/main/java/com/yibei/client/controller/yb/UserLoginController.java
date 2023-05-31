package com.yibei.client.controller.yb;


import cn.hutool.http.HttpRequest;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.client.controller.BaseController;
import com.yibei.common.config.WxPayConfig;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.SecurityUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.common.utils.WeChatApiUtil;
import com.yibei.common.utils.WxUtils;
import com.yibei.system.domain.User;
import com.yibei.system.service.IUserService;
import com.yibei.yb.domain.bo.WeChatDecodeBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController("Client_UserLoginController")
@Api(value = "用户登录模块",tags = "用户登录模块")
@RequestMapping("/client/userlogin")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserLoginController extends BaseController {

    @Value("${clientToken.secret}")
    private String secret;
    @Value("${clientToken.expireTime}")
    private Integer expireTime;

    private final IUserService iUserService;
    private final WxPayConfig wxPayConfig;


    @ApiOperation("微信登录")
    @PostMapping("/weLongin")
    public AjaxResult weLongin( @RequestBody @Validated WeChatDecodeBo bo) {

//        String unionid = null;
        String openid = null;
        try {
            if(StringUtils.isBlank(bo.getCode())){
                return AjaxResult.error("code不能为空");
            }
            //小程序获取unionid
            String URL = "https://api.weixin.qq.com/sns/jscode2session?appid="+ wxPayConfig.getAppId() +"&secret="+ wxPayConfig.getAppSecret() +"&js_code="+ bo.getCode() +"&grant_type=authorization_code";
            String result = HttpRequest.get(URL)
                    .timeout(20000)
                    .execute().body();
            JSONObject jsonObject = JSONObject.fromObject(result);

//            if(jsonObject.containsKey("unionid")){
//                unionid = jsonObject.getString("unionid");
//            }
            if(jsonObject.containsKey("openid")){
                openid = jsonObject.getString("openid");
            }
            if(openid == null){
                return AjaxResult.error("获取微信信息失败");
            }

            User user = iUserService.getOne(new LambdaQueryWrapper<User>().eq(User::getAppletOpenid, openid).last("limit 1"));
            /*if(unionid != null){
                user = iUserService.getOne(new LambdaQueryWrapper<User>().eq(User::getWeChat, unionid).last("limit 1"));
            }else{
                user = iUserService.getOne(new LambdaQueryWrapper<User>().eq(User::getAppletOpenid, openid).last("limit 1"));
            }*/

            if(user == null){
                user = new User();
//                user.setWeChat(unionid);
                user.setAppletOpenid(openid);
                user.setPassword(SecurityUtils.encryptPassword("123456"));
                user.setNickName("新用户");

                user.setAvatar(bo.getAvatar());
                user.setNickName(bo.getNickName());

                iUserService.save(user);
                return AjaxResult.success("token",doLogin(user));
            }else{
                if(StringUtils.isBlank(bo.getAvatar()))user.setAvatar(bo.getAvatar());
                if(StringUtils.isBlank(bo.getNickName()))user.setNickName(bo.getNickName());
                if(StringUtils.isBlank(bo.getAvatar()) || StringUtils.isBlank(bo.getNickName())){
                    iUserService.updateById(user);
                }

                return AjaxResult.success("token",doLogin(user));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("获取失败");
        }
    }


    private String doLogin(User user){
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;
            {
                put("uid", user.getId().toString());
                put("expire_time", System.currentTimeMillis() + 1000 * 60 * expireTime);
            }
        };
        return JWTUtil.createToken(map, secret.getBytes());
    }

    @ApiOperation("微信小程序AccessToken")
    @PostMapping("/getAccessToken")
    public AjaxResult getAccessToken() {

        return AjaxResult.success("accessToken",WeChatApiUtil.getAccessToken());
    }


}
