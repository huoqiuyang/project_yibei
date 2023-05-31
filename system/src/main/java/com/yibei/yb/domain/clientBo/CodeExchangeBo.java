package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CodeExchangeBo {

    @ApiModelProperty(value = "兑换码")
    @NotBlank(message = "兑换码不能为空")
    private String code;

}
