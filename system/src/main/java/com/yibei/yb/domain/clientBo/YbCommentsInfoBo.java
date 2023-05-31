package com.yibei.yb.domain.clientBo;

import com.yibei.system.domain.clientBo.PageBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbCommentsInfoBo extends PageBo {

    @ApiModelProperty("内容id")
    private Long contentId;

    @ApiModelProperty("类型(1教材2题库3拓展阅读)")
    private Long sourceType;

    @ApiModelProperty("上级id")
    private Long parentId;

}
