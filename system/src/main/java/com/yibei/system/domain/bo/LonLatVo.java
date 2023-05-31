package com.yibei.system.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LonLatVo {
    @ApiModelProperty("经度")
    @NotBlank(message = "请输入经度")
    private String lon;
    @ApiModelProperty("纬度")
    @NotBlank(message = "请输入纬度")
    private String lat;
}
