package com.yibei.common.core.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Entity基类
 *
 * @author
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 搜索值
	 */
	@ApiModelProperty(value = "搜索值")
	private String searchValue;

	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者")
	private String createBy;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 更新者
	 */
	@ApiModelProperty(value = "更新者")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 请求参数
	 */
	@ApiModelProperty(value = "请求参数")
	private Map<String, Object> params = new HashMap<>();

}
