package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YbUserBookshelfIsJoinBo {

    @ApiModelProperty("教材题库类型（1教材2题库）")
    @NotNull
    private Long contentType;

    @ApiModelProperty("教材题库id")
    @NotNull
    private Long id;

}
