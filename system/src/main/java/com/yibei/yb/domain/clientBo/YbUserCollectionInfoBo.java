package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YbUserCollectionInfoBo {

    @ApiModelProperty("教材id/题库id")
    @NotNull
    private Long id;

//    @ApiModelProperty("类型：1目录 2收藏")
//    @NotNull
//    private Integer type;

}
