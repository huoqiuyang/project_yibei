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
 * 报错视图对象 yb_report_errors
 *
 * @author yibei
 * @date 2022-05-18
 */
@Data
@ApiModel("报错视图对象")
@ExcelIgnoreUnannotated
public class YbReportErrorsVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 词条id
     */
	@ExcelProperty(value = "词条id")
	@ApiModelProperty("词条id")
	private Long entryId;

    /**
     * 内容
     */
	@ExcelProperty(value = "内容")
	@ApiModelProperty("内容")
	private String content;

    /**
     * 图片
     */
	@ExcelProperty(value = "图片")
	@ApiModelProperty("图片")
	private String imgUrl;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
