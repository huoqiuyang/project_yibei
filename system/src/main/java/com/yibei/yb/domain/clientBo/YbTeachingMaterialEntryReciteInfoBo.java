package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YbTeachingMaterialEntryReciteInfoBo {

    @ApiModelProperty("教材id")
    @NotNull
    private Long teachingMaterialId;

    @ApiModelProperty("操作类型：0默认打开背诵，1跳下一个词条")
    @NotNull
    private int operationType = 0;

}
