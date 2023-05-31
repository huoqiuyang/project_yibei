package com.yibei.system.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VerificationCodeSendBo {
    @ApiModelProperty(value = "1.注册 2.登录 3.修改密码")
    @NotNull(message = "NotNull type")
    private Integer type;

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "NotBlank account")
    private String account;

}
