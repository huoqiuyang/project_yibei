package com.yibei.yb.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PhoneBo {

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号不能为空")
    private String phone;
}
