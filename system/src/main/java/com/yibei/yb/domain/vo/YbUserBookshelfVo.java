package com.yibei.yb.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 用户书架视图对象 yb_user_bookshelf
 *
 * @author yibei
 * @date 2022-05-06
 */
@Data
@ApiModel("用户书架视图对象")
@ExcelIgnoreUnannotated
public class YbUserBookshelfVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 教材题库类型
     */
    @ExcelProperty(value = "教材题库类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "content_type")
	@ApiModelProperty("教材题库类型")
	private Long contentType;

    /**
     * 内容id
     */
	@ExcelProperty(value = "内容id")
	@ApiModelProperty("内容id")
	private Long contentId;

    /**
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 最近打开时间
     */
	@ExcelProperty(value = "最近打开时间")
	@ApiModelProperty("最近打开时间")
	private Date lastOpenTime;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
