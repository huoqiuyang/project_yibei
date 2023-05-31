package com.yibei.system.domain.bo;

import com.yibei.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 【请填写功能名称】分页查询对象 city_info
 *
 * @author frame
 * @date 2021-07-14
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("【请填写功能名称】分页查询对象")
public class CityInfoQueryBo extends BaseEntity {

	/** 分页大小 */
	@ApiModelProperty("分页大小")
	private Integer pageSize=20;

	/** 当前页数 */
	@ApiModelProperty("当前页数")
	private Integer pageNum=1;

	/** 排序列 */
	@ApiModelProperty("排序列")
	private String orderByColumn;

	/** 排序的方向desc或者asc */
	@ApiModelProperty(value = "排序的方向", example = "asc,desc")
	private String isAsc;

	/** $column.columnComment */
	@ApiModelProperty("$column.columnComment")
	private Integer parentId;

	/** $column.columnComment */
	@ApiModelProperty("$column.columnComment")
	private String name;

	/** 区域代码 */
	@ApiModelProperty("区域代码")
	private String zipcode;

	/** 地区级别 1省 2市 3区 */
	@ApiModelProperty("地区级别 1省 2市 3区")
	private Integer regionLevel;

	/** 0未开通 1已开通 */
	@ApiModelProperty("0未开通 1已开通")
	private Integer state;

	/** $column.columnComment */
	@ApiModelProperty("$column.columnComment")
	private Integer isDeleted;

}
