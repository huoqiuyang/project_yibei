package com.yibei.system.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TreeDataVo {
    @ApiModelProperty("上级id")
    private Integer parentId=0;
    @ApiModelProperty("子级key名称")
    private String childrenKey="sub";
}
