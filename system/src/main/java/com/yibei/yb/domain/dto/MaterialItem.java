package com.yibei.yb.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MaterialItem {

    @ApiModelProperty("教材ID")
    private Long materialId;

    @ApiModelProperty("词条标题")
    private String title;

    @ApiModelProperty("重要度")
    private Integer importance;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("教材名称")
    private String materialName;
}
