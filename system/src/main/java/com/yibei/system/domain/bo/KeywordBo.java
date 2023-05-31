package com.yibei.system.domain.bo;

import com.yibei.system.domain.clientBo.PageBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class KeywordBo extends PageBo {

    @ApiModelProperty("关键字")
    @NotBlank(message = "关键字不能为空")
    private String keyword;

    @ApiModelProperty("来源 'pc'  'applet'  'app'")
    private String source;
}
