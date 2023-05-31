package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class YbReportErrorsInfoBo {

    @ApiModelProperty("内容")
    @NotBlank
    private String content;

    @ApiModelProperty("图片")
    private String imgUrl;

    @ApiModelProperty("词条id")
    private Long entryId;

}
