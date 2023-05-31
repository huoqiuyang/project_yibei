package com.yibei.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 【请填写功能名称】对象 city_region
 *
 * @author frame
 * @date 2021-07-14
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("`city_region`")
public class CityRegion implements Serializable {

private static final long serialVersionUID=1L;

	/** $column.columnComment */
	@TableId(value = "id")
	@TableField(value = "`id`")
	private Integer id;

	/** $column.columnComment */
	@TableField(value = "`city_id`")
	private Integer cityId;

	/** $column.columnComment */
	@TableField(value = "`name`")
	private String name;

	/** 排序 */
	@TableField(value = "`sort`")
	private Integer sort;

	/** 0未开通 1已开通 */
	@TableField(value = "`state`")
	private Integer state;

	/** $column.columnComment */
	@TableLogic
	@TableField(value = "`is_deleted`")
	private Integer isDeleted;

	/** $column.columnComment */
	@TableField(value = "`create_time`")
	private LocalDateTime createTime;
}
