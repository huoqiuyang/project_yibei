package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ReportSigninInfoBo {

    @ApiModelProperty("月份（yyyy-MM）")
    @NotBlank
    private String month;

}
