package com.yibei.system.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WeatherBo {

    @ApiModelProperty(value = "城市名称")
    @NotBlank(message = "城市名称不能为空")
    private String name;

}
