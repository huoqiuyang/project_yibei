package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportStudyReadVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("展示图")
    private String imgUrl;

    @ApiModelProperty("词条总数")
    private long counts;

    @ApiModelProperty("已学词条数量")
    private long isRead;

    @ApiModelProperty("学习分钟数")
    private long min;

    @ApiModelProperty("已学百分比")
    private String proportion;

    @ApiModelProperty("预计还需小时数")
    private int hourNum;

    @ApiModelProperty("是否显示预计剩余时长（0不显示1显示）")
    private Integer isDisplay;

}
