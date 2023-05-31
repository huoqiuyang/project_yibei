package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YbExpandReadingUpdateStatusBo {

    @ApiModelProperty("拓展阅读ID")
    @NotNull
    private Long id;

    @ApiModelProperty("阅读状态（0未学习1已学习2学习中3已学完）")
    @NotNull
    private int readStatus;

}
