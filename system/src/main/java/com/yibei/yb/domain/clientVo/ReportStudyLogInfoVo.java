package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportStudyLogInfoVo {

    @ApiModelProperty("日期")
    private String days;

    @ApiModelProperty("信息")
    private String info;

    @ApiModelProperty("阅读分钟数")
    private int readNum;

    @ApiModelProperty("背诵个数")
    private int reciteNum;

    @ApiModelProperty("测试题目数")
    private int questionBankNum;

    @ApiModelProperty("拓展阅读学习篇数")
    private int expandReadingNum;

}
