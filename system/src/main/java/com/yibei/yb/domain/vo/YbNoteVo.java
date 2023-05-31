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
 * 笔记视图对象 yb_note
 *
 * @author yibei
 * @date 2022-05-11
 */
@Data
@ApiModel("笔记视图对象")
@ExcelIgnoreUnannotated
public class YbNoteVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 教材id
     */
	@ExcelProperty(value = "教材id")
	@ApiModelProperty("教材id")
	private Long teachingMaterialId;

    /**
     * 词条id
     */
	@ExcelProperty(value = "词条id")
	@ApiModelProperty("词条id")
	private Long entryId;

    /**
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 内容
     */
	@ExcelProperty(value = "内容")
	@ApiModelProperty("内容")
	private String content;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
