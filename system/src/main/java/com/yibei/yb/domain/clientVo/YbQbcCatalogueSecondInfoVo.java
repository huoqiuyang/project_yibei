package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbQbcCatalogueSecondInfoVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("重要度")
    private Integer importance;

    @ApiModelProperty("状态类型：0未做1知道2不知道")
    private int type;

}
