package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportStudyExpandReadingVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("展示图")
    private String imgUrl;

}
