package com.yibei.admin.controller.system;

import com.yibei.common.utils.SecurityUtils;
import com.yibei.framework.security.AllowAnonymous;
import com.yibei.framework.web.service.SysLoginService;
import com.yibei.framework.web.service.SysPermissionService;
import com.yibei.system.service.ISysMenuService;
import com.yibei.common.constant.Constants;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.domain.entity.SysMenu;
import com.yibei.common.core.domain.entity.SysUser;
import com.yibei.common.core.domain.model.LoginBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @author
 */
@RestController
@RequestMapping("/admin")
public class SysLoginController
{
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    @AllowAnonymous
    public AjaxResult login(@RequestBody LoginBody loginBody)
    {
		Map<String,Object> ajax = new HashMap<>();
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        return AjaxResult.success(ajax);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
		Map<String,Object> ajax = new HashMap<>();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return AjaxResult.success(ajax);
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters()
    {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
