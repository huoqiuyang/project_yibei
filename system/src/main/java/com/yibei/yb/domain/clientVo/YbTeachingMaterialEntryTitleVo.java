package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbTeachingMaterialEntryTitleVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("教材id")
    private Long teachingMaterialId;

    @ApiModelProperty("上级id")
    private Long parentId;

    @ApiModelProperty("标题")
    private String title;

}
