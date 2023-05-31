package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbUpvoteLogInfoBo {

    @ApiModelProperty("评论id")
    private Long commentsId;

    @ApiModelProperty("操作类型(1点赞0取消点赞)")
    private Integer operationType;

}
