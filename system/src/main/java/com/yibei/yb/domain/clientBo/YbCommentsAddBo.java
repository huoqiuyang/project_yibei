package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbCommentsAddBo {

    @ApiModelProperty("内容id")
    private Long contentId;

    @ApiModelProperty("类型(1教材2题库3拓展阅读)")
    private Long sourceType;

    @ApiModelProperty("上级id")
    private Long parentId;

    @ApiModelProperty("评论内容")
    private String comments;

}
