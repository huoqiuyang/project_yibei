package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbUserBookshelfTmInfoVo {

    @ApiModelProperty("词条数")
    private long entryNum;

    @ApiModelProperty("是否有背诵计划（0没有，1有）")
    private long isRecite;

    @ApiModelProperty("已读百分比")
    private String proportion;

    @ApiModelProperty("今日需背诵词条数")
    private int reciteNum;

}
