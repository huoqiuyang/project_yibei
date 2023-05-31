package com.yibei.yb.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @auther zhangChunLin
 * @create 2021-03-08-16:17
 */

@Data
public class WxAliRegisterLoginBo {

    /*@ApiModelProperty(value = "类别 1微信；2支付宝 3苹果")
    @NotNull(message = "类别不能为空")
    @Max(3)
    @Min(1)
    private Integer type;*/

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;

    /*@ApiModelProperty(value = "微信unionid / 支付宝 userid / 苹果id")
    @NotBlank(message = "微信unionid / 支付宝 userid / 苹果id 不能为空")
    private String account;*/

    @ApiModelProperty(value = "微信unionid")
    @NotBlank(message = "微信unionid")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
