package com.yibei.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class BaseVo {
	/** 关联数据 */
	@ApiModelProperty("关联数据")
	private Map<String,Object> extData = new HashMap<>();
}
