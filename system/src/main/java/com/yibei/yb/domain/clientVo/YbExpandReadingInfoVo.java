package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbExpandReadingInfoVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("描述")
    private String describe;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("展示图")
    private String imgUrl;

    @ApiModelProperty("音频")
    private String audioFrequency;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("阅读状态（0未学习1已学习2学习中3已学完）")
    private Long readStatus=0l;

}
