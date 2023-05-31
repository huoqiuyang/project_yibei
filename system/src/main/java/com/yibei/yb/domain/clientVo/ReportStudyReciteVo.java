package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportStudyReciteVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("展示图")
    private String imgUrl;

    @ApiModelProperty("背诵计划词条总数")
    private long counts;

    @ApiModelProperty("已背词条数量")
    private long isRecite;

    @ApiModelProperty("学习分钟数")
    private long min;

    @ApiModelProperty("预计还需天数")
    private int dayNum;

    @ApiModelProperty("是否显示预计剩余时长（0不显示1显示）")
    private int isDisplay;

}
