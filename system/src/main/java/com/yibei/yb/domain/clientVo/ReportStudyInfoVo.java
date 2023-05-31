package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReportStudyInfoVo {

    @ApiModelProperty("阅读进度")
    private List<ReportStudyReadVo> readListVo;

    @ApiModelProperty("背诵进度")
    private List<ReportStudyReciteVo> reciteListVo;

    @ApiModelProperty("模拟测试")
    private List<ReportStudyQuestionBankVo> questionBankListVo;

    @ApiModelProperty("拓展阅读")
    private List<ReportStudyExpandReadingVo> expandReadingListVo;

    @ApiModelProperty("拓展阅读-已读篇数")
    private int expandReadingNum;

}
