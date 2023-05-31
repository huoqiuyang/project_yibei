package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class YbUserCollectionAddBo {

    @ApiModelProperty("操作类型（1加入2移出）")
    @NotNull
    @Max(2)
    @Min(1)
    private Integer operationType;

    @ApiModelProperty("id")
    @NotNull
    private Long id;

}
