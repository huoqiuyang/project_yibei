package com.yibei.system.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PageBo {
    @ApiModelProperty("页码")
    private Integer pageNum = 1;
    @ApiModelProperty("页大小")
    private Integer pageSize = 20;
}
