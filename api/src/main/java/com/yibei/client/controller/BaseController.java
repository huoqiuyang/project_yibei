package com.yibei.client.controller;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTHeader;
import cn.hutool.jwt.JWTUtil;
import com.yibei.common.utils.StringUtils;
import com.yibei.system.domain.User;
import com.yibei.system.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected IUserService iUserService;

    /**
     * 获取登录的用户ID
     * @return
     */
    protected Long getUserId(){
        try {
            String token = request.getHeader("Authorization");
            if(StringUtils.isBlank(token)){
                return null;
            }
            JWT jwt = JWTUtil.parseToken(token);
            return  Long.parseLong(jwt.getPayload("uid").toString()) ;
        }catch (Exception ex){
            return null;
        }
    }

    /**
     * 获取登录的用户
     * @return
     */
    protected User getUserByLogin(){
        try {
            Long userId = getUserId();
            if(userId == null){
                return null;
            }
            return iUserService.getById(userId);
        }catch (Exception ex){
            return null;
        }
    }

}
