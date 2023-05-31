package com.yibei.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class VerificationCodeSendVo {

    @ApiModelProperty(value = "验证码uuid")
    private String uuid;

}
