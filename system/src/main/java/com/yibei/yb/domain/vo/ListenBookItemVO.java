package com.yibei.yb.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListenBookItemVO {

    @ApiModelProperty(value = "教材ID")
    private Long materialId;

    @ApiModelProperty(value = "教材名称")
    private String materialName;

    @ApiModelProperty(value = "展示图url")
    private String imgUrl;

    @ApiModelProperty(value = "已听百分比")
    private String proportion;

    @ApiModelProperty(value = "上次听的词条名称")
    private String entryName;

    @ApiModelProperty(value = "上次听的词条id，这个值为null说明该书未听")
    private Long entryId;

    @ApiModelProperty(value = "上次听的词条的位置")
    private String lastLocation;

}
