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
 * 题库内容视图对象 yb_question_bank_content
 *
 * @author yibei
 * @date 2022-05-06
 */
@Data
@ApiModel("题库内容视图对象")
@ExcelIgnoreUnannotated
public class YbQuestionBankContentVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 题库id
     */
	@ExcelProperty(value = "题库id")
	@ApiModelProperty("题库id")
	private Long questionBankId;

    /**
     * 上级id
     */
	@ExcelProperty(value = "上级id")
	@ApiModelProperty("上级id")
	private Long parentId;

    /**
     * 标题
     */
	@ExcelProperty(value = "标题")
	@ApiModelProperty("标题")
	private String title;

    /**
     * 答案
     */
	@ExcelProperty(value = "答案")
	@ApiModelProperty("答案")
	private String answer;

    /**
     * 图片
     */
	@ExcelProperty(value = "图片")
	@ApiModelProperty("图片")
	private String imgUrl;

    /**
     * 标签
     */
    @ExcelProperty(value = "标签", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "label_type")
	@ApiModelProperty("标签")
	private String label;

    /**
     * 重要度
     */
    @ExcelProperty(value = "重要度", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "importance_type")
	@ApiModelProperty("重要度")
	private Integer importance;

	/**
	 * 相关链接
	 */
	@ExcelProperty(value = "相关链接")
	@ApiModelProperty("相关链接")
	private String relatedLinks;

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
