package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YbTeachingMaterialEntryReadInfoBo {

    @ApiModelProperty("教材id")
    @NotNull
    private Long teachingMaterialId;

    @ApiModelProperty("词条id")
    private Long id;

}
