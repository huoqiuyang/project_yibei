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
public class AppleLoginBo {

    @ApiModelProperty(value = "类别 1绑定；2登陆")
    @NotNull(message = "类别不能为空")
    @Max(2)
    @Min(1)
    private Integer type;

    @ApiModelProperty(value = "来源 1 web 2 app 3 小程序 4 H5")
    private Integer source;

    @ApiModelProperty(value = "苹果appleid")
    @NotBlank(message = "苹果appleid不能为空")
    private String appleid;

}
