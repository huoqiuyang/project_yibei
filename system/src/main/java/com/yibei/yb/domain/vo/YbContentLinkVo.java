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
 * 相关链接视图对象 yb_content_link
 *
 * @author yibei
 * @date 2022-05-06
 */
@Data
@ApiModel("相关链接视图对象")
@ExcelIgnoreUnannotated
public class YbContentLinkVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 来源类型
     */
    @ExcelProperty(value = "来源类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "source_type")
	@ApiModelProperty("来源类型")
	private Long sourceType;

    /**
     * 词条id
     */
	@ExcelProperty(value = "词条id")
	@ApiModelProperty("词条id")
	private Long entryId;

	/**
	 * 链接词条来源类型
	 */
	@ExcelProperty(value = "链接词条来源类型", converter = ExcelDictConvert.class)
	@ExcelDictFormat(dictType = "source_type")
	@ApiModelProperty("链接词条来源类型")
	private Long linkSourceType;

    /**
     * 链接词条id
     */
	@ExcelProperty(value = "链接词条id")
	@ApiModelProperty("链接词条id")
	private Long linkEntryId;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
