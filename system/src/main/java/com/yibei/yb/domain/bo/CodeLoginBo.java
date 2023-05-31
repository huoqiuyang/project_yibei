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
public class CodeLoginBo {

    @ApiModelProperty(value = "code")
    private String code;

}
