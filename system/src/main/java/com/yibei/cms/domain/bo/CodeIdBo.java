package com.yibei.cms.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CodeIdBo {
	@ApiModelProperty("文章code")
	private String code;
	@ApiModelProperty("文章id")
	private Integer id;
}
