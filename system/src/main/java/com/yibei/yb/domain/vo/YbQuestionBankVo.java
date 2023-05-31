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
 * 题库视图对象 yb_question_bank
 *
 * @author yibei
 * @date 2022-05-06
 */
@Data
@ApiModel("题库视图对象")
@ExcelIgnoreUnannotated
public class YbQuestionBankVo extends BaseVo {

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
