package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class YbUserBookshelfAddBo {

    @ApiModelProperty("操作类型（1加入书架2移出书架）")
    @NotNull
    @Max(2)
    @Min(1)
    private Integer operationType;

    @ApiModelProperty("教材题库类型（1教材2题库）")
    @NotNull
    private Long contentType;

    @ApiModelProperty("内容id")
    @NotNull
    private Long contentId;

}
