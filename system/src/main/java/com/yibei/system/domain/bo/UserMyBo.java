package com.yibei.system.domain.bo;

import com.yibei.common.core.domain.BaseEntity;
import com.yibei.common.core.validate.AddGroup;
import com.yibei.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户业务对象 user
 *
 * @author yibei
 * @date 2021-12-15
 */

@Data
@ApiModel("用户业务对象")
public class UserMyBo {

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱", required = true)
//    @NotBlank(message = "用户邮箱不能为空", groups = { AddGroup.class })
    private String email;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码不能为空", groups = { AddGroup.class })
    private String password;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String phone;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String avatar;

    /**
     * 微信
     */
    @ApiModelProperty(value = "微信")
    private String weChat;

}
