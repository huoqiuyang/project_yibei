package com.yibei.framework.sso;

import cn.hutool.http.HttpStatus;
import cn.hutool.jwt.JWTUtil;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.JsonUtils;
import com.yibei.common.utils.ServletUtils;
import com.yibei.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginCheckedInterceptor implements HandlerInterceptor {

    @Value("${clientToken.secret}")
    private String secret;
    @Value("${clientToken.expireTime}")
    private Integer expireTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        HandlerMethod method = (HandlerMethod) handler;
        LoginChecked filter = method.getMethodAnnotation(LoginChecked.class);
        if(filter==null){
            //没有注解直接放行
            return true;
        }

        //有注解，验证登录
        return doFilter(request,response);
    }

    public boolean doFilter(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");

        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)){
            notLogin(request,response);
            return false;
        }

        //验证token
        if(!JWTUtil.verify(token, secret.getBytes())){
            notLogin(request,response);
            return false;
        }
        return true;

    }

    public void notLogin(HttpServletRequest request, HttpServletResponse response){
        int code = HttpStatus.HTTP_UNAUTHORIZED;
        String msg = StringUtils.format("{}", request.getRequestURI());
        ServletUtils.renderString(response, JsonUtils.toJsonString(AjaxResult.error(code, msg)));
    }
}
