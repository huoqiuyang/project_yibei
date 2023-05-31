package com.yibei.system.domain.bo;

import com.yibei.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 【请填写功能名称】分页查询对象 city_region
 *
 * @author frame
 * @date 2021-07-14
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("【请填写功能名称】分页查询对象")
public class CityRegionQueryBo extends BaseEntity {

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
	private Integer cityId;

	/** $column.columnComment */
	@ApiModelProperty("$column.columnComment")
	private String name;

	/** 排序 */
	@ApiModelProperty("排序")
	private Integer sort;

	/** 0未开通 1已开通 */
	@ApiModelProperty("0未开通 1已开通")
	private Integer state;

	/** $column.columnComment */
	@ApiModelProperty("$column.columnComment")
	private Integer isDeleted;

}
