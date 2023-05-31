package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbQuestionBankContentTitleVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("题库id")
    private Long questionBankId;

    @ApiModelProperty("上级id")
    private Long parentId;

    @ApiModelProperty("标题")
    private String title;

}
