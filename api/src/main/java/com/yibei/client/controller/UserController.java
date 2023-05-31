package com.yibei.client.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.validate.EditGroup;
import com.yibei.common.utils.SecurityUtils;
import com.yibei.common.utils.TimeUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.system.domain.User;
import com.yibei.system.domain.bo.PasswordEditBo;
import com.yibei.system.domain.bo.PhonePasswordEditBo;
import com.yibei.system.domain.bo.UserMyBo;
import com.yibei.system.domain.clientVo.UserInfoVo;
import com.yibei.system.service.IUserService;
import com.yibei.yb.domain.YbUserVip;
import com.yibei.yb.service.IYbUserVipService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController("Client_UserController")
@Api(value = "用户模块",tags = "用户模块")
@RequestMapping("/client/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController extends BaseController {

    private final IUserService iUserService;

    @ApiOperation("获取用户信息")
    @PostMapping("/info")
    @LoginChecked
    public AjaxResult<UserInfoVo> info() {
        User user = getUserByLogin();
        UserInfoVo vo = BeanUtil.toBean(user, UserInfoVo.class);

        //会员信息
        YbUserVip userVip = iUserService.getUserVipInfo(vo.getId());
        if(userVip!=null){
            vo.setVipStatus(1);
            vo.setVipEndTime(userVip.getEndTime());
        }else{
            vo.setVipStatus(0);
        }

        return AjaxResult.success(vo);
    }

    @ApiOperation("编辑个人信息")
    @PostMapping("/edit")
    @LoginChecked
    public AjaxResult edit(@RequestBody @Validated(EditGroup.class) UserMyBo bo) {
        User user = new User();
        BeanUtils.copyProperties(bo,user);
        user.setId(getUserId());
//        if(StringUtils.isNotBlank(bo.getPhone())){
//            if(iUserService.count(new LambdaQueryWrapper<User>().eq(User::getPhone,bo.getPhone()))>0){
//                return AjaxResult.error("手机号已存在");
//            }
//        }
        if(iUserService.updateById(user)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error();
        }
    }

    @ApiOperation("旧密码修改密码")
    @PostMapping("/editPassword")
    @LoginChecked
    public AjaxResult editPassword(@RequestBody @Validated(EditGroup.class) PasswordEditBo bo) {
        User user = getUserByLogin();
        if(!SecurityUtils.matchesPassword(bo.getOldPassword(),user.getPassword())){
            return AjaxResult.error("旧密码不正确");
        }
        User u = new User();
        u.setId(user.getId());
        u.setPassword(SecurityUtils.encryptPassword(bo.getPassword()));
        if(iUserService.updateById(u)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error();
        }
    }

    @ApiOperation("手机号修改密码")
    @PostMapping("/phoneEditPassword")
    public AjaxResult phoneEditPassword(@RequestBody @Validated(EditGroup.class) PhonePasswordEditBo bo) {

        User one = iUserService.getOne(new LambdaQueryWrapper<User>().eq(User::getPhone, bo.getPhone()).last("limit 1"));
        if(one == null){
            return AjaxResult.error("用户不存在");
        }
        User u = new User();
        u.setId(one.getId());
        u.setPassword(SecurityUtils.encryptPassword(bo.getPassword()));
        if(iUserService.updateById(u)){
            return AjaxResult.success();
        }else {
            return AjaxResult.error();
        }
    }

}
