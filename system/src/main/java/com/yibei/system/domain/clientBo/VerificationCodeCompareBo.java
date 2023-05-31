package com.yibei.system.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class VerificationCodeCompareBo extends VerificationCodeSendBo {

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "NotBlank code")
    private String code;
}
