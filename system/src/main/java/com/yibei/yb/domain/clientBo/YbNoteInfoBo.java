package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class YbNoteInfoBo {

    @ApiModelProperty("词条id")
    @NotNull
    private Long entryId;

    @ApiModelProperty("内容")
    private String content;

}
