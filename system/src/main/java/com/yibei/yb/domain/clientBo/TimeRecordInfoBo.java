package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TimeRecordInfoBo {

    @ApiModelProperty("教材id/题库id/拓展阅读id")
    @NotNull
    private Long id;

    @ApiModelProperty("学习类型（1阅读2背诵3做题4拓展阅读）")
    @NotNull
    private Integer studyType;

    @ApiModelProperty("学习时间（分钟）")
    @NotNull
    private Long min;

}
