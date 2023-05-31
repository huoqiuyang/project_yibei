package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbTeachingMaterialListVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("展示图")
    private String imgUrl;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("内容")
    private String content;


    @ApiModelProperty("词条数")
    private long entryNum;

    @ApiModelProperty("是否加入书架（0未加入书架，1已加入书架）")
    private long isBookshelf;

    @ApiModelProperty("是否有背诵计划（0没有，1有）")
    private long isRecite;

    @ApiModelProperty("已读百分比")
    private String proportion;

    @ApiModelProperty("今日需背诵词条数")
    private int reciteNum;

}
