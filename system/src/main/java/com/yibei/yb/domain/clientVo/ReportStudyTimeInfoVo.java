package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportStudyTimeInfoVo {

    @ApiModelProperty("总计学习（分钟）")
    private int totalStudyMin;

    @ApiModelProperty("本月学习（分钟）")
    private int monthStudyMin;

    @ApiModelProperty("本周学习（分钟）")
    private int weekStudyMin;

}
