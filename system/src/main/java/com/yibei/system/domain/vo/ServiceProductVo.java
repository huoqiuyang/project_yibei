package com.yibei.system.domain.vo;

import java.util.Date;

import com.yibei.system.vo.BaseVo;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yibei.common.annotation.ExcelDictFormat;
import com.yibei.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * DDE服务产品视图对象 service_product
 *
 * @author dde
 * @date 2021-10-22
 */
@Data
@ApiModel("DDE服务产品视图对象")
@ExcelIgnoreUnannotated
public class ServiceProductVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  id
     */
	@ExcelProperty(value = "id")
	@ApiModelProperty("id")
	private Long id;

    /**
     * 标题
     */
	@ExcelProperty(value = "标题")
	@ApiModelProperty("标题")
	private String title;

    /**
     * 展示图
     */
	@ExcelProperty(value = "展示图")
	@ApiModelProperty("展示图")
	private String imgUrl;

	/**
	 * 描述
	 */
	@ExcelProperty(value = "描述")
	@ApiModelProperty("描述")
	private String describe;

	/**
	 * 内容
	 */
	@ExcelProperty(value = "内容")
	@ApiModelProperty("内容")
	private String content;

    /**
     * 排序
     */
	@ExcelProperty(value = "排序")
	@ApiModelProperty("排序")
	private Long sort;

    /**
     * 状态(0否1是显示)
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "service_status")
	@ApiModelProperty("状态(0否1是显示)")
	private Integer status;

    /**
     * 发布时间
     */
	@ExcelProperty(value = "发布时间")
	@ApiModelProperty("发布时间")
	private Date publishTime;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
