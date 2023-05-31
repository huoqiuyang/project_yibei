package com.yibei;

import com.yibei.client.controller.BaseController;
import com.yibei.common.config.SiteConfig;
import com.yibei.common.utils.MessageUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.framework.security.AllowAnonymous;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 首页
 *
 * @author
 */
@RestController
@Api(value = "Index模块")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class IndexController extends BaseController
{
    /** 系统基础配置 */
    private final SiteConfig siteConfig;

    /**
     * 访问首页，提示语
     */
    @GetMapping("/")
    @AllowAnonymous
    public String index()
    {
        return StringUtils.format("【{}】【{}】 api service started successfully ...", siteConfig.getName(), LocalDateTime.now().toString());
    }

    @GetMapping("/test")
    @AllowAnonymous
    public String test(){
        return MessageUtils.message("user.password.not.match");
    }

}
