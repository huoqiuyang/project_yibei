package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudyInfoBo {

    @ApiModelProperty("教材id")
    @NotNull
    private Long id;

}
