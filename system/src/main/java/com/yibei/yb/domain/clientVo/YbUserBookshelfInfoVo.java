package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbUserBookshelfInfoVo {

    @ApiModelProperty("书架id")
    private Long bookshelfId;

    @ApiModelProperty("教材题库类型（1教材2题库）")
    private Long contentType;

    @ApiModelProperty("教材/题库 ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("展示图")
    private String imgUrl;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("教材学习信息")
    private YbUserBookshelfTmInfoVo tmInfo;

    @ApiModelProperty("题库学习信息")
    private YbUserBookshelfQbInfoVo qbInfo;

}
