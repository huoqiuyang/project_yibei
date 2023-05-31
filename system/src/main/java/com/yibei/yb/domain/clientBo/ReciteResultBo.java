package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ReciteResultBo {

    @ApiModelProperty("词条id")
    @NotNull
    private Long entryId;

    @ApiModelProperty("背诵结果（1知道2不知道）")
    @NotNull
    private Integer result;

}
