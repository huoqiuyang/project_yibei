package com.yibei.cms.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CodeBo {
	@NotBlank
	@NotBlank(message = "code不能为空")
	private String code;
}
