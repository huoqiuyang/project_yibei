package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YbQuestionBankContentInfoBo {

    @ApiModelProperty("题库id")
    @NotNull
    private Long questionBankId;

    @ApiModelProperty("题目id")
    private Long id;

}
