package com.yibei.yb.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yibei.common.annotation.ExcelDictFormat;
import com.yibei.common.convert.ExcelDictConvert;
import com.yibei.system.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 拓展阅读视图对象 yb_expand_reading
 *
 * @author yibei
 * @date 2022-05-11
 */
@Data
@ApiModel("拓展阅读视图对象")
@ExcelIgnoreUnannotated
public class YbExpandReadingVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 标题
     */
	@ExcelProperty(value = "标题")
	@ApiModelProperty("标题")
	private String title;

    /**
     * 描述
     */
	@ExcelProperty(value = "描述")
	@ApiModelProperty("描述")
	private String describe;

    /**
     * 标签
     */
	@ExcelProperty(value = "标签")
	@ApiModelProperty("标签")
	private String label;

    /**
     * 展示图
     */
	@ExcelProperty(value = "展示图")
	@ApiModelProperty("展示图")
	private String imgUrl;

    /**
     * 音频
     */
	@ExcelProperty(value = "音频")
	@ApiModelProperty("音频")
	private String audioFrequency;

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
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "display_status")
	@ApiModelProperty("状态")
	private Integer status;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
