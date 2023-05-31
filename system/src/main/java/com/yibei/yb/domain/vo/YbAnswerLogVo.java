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
 * 答题记录视图对象 yb_answer_log
 *
 * @author yibei
 * @date 2022-05-07
 */
@Data
@ApiModel("答题记录视图对象")
@ExcelIgnoreUnannotated
public class YbAnswerLogVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 题目id
	 */
	@ExcelProperty(value = "题目id")
	@ApiModelProperty("题目id")
	private Long questionBankContentId;

	/**
	 * 题库id
	 */
	@ExcelProperty(value = "题库id")
	@ApiModelProperty("题库id")
	private Long questionBankId;

    /**
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "answer_type")
	@ApiModelProperty("类型")
	private Integer type;

	/**
	 * 修改时间
	 */
	@ExcelProperty(value = "修改时间")
	@ApiModelProperty("修改时间")
	private Date lastTime;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
