package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class YbAnswerLogInfoBo {

    @ApiModelProperty("题目id")
    @NotNull
    private Long id;

    @ApiModelProperty("类型（1知道2不知道）")
    @NotNull
    @Max(2)
    @Min(1)
    private Integer type;

}
