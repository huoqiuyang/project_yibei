package com.yibei.yb.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AccessTokenBo {

    @ApiModelProperty("accessToken")
    @NotBlank(message = "accessToken不能为空")
    private String accessToken;
}
