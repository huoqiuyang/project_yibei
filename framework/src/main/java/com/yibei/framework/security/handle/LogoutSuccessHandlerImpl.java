package com.yibei.framework.security.handle;

import cn.hutool.http.HttpStatus;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.domain.model.LoginUser;
import com.yibei.common.utils.JsonUtils;
import com.yibei.common.utils.ServletUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.framework.web.service.AsyncService;
import com.yibei.framework.web.service.TokenService;
import com.yibei.common.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义退出处理类 返回成功
 *
 * @author
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AsyncService asyncService;

	/**
	 * 退出处理
	 */
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
		throws IOException, ServletException {
		LoginUser loginUser = tokenService.getLoginUser(request);
		if (StringUtils.isNotNull(loginUser)) {
			String userName = loginUser.getUsername();
			// 删除用户缓存记录
			tokenService.delLoginUser(loginUser.getToken());
			// 记录用户退出日志
			asyncService.recordLogininfor(userName, Constants.LOGOUT, "退出成功", request);
		}
		ServletUtils.renderString(response, JsonUtils.toJsonString(AjaxResult.error(HttpStatus.HTTP_OK, "退出成功")));
	}

}
