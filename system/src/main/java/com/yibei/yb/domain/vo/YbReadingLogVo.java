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
 * 阅读记录视图对象 yb_reading_log
 *
 * @author yibei
 * @date 2022-05-11
 */
@Data
@ApiModel("阅读记录视图对象")
@ExcelIgnoreUnannotated
public class YbReadingLogVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 拓展阅读id
     */
	@ExcelProperty(value = "拓展阅读id")
	@ApiModelProperty("拓展阅读id")
	private Long expandReadId;

    /**
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 阅读状态
     */
    @ExcelProperty(value = "阅读状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "read_status")
	@ApiModelProperty("阅读状态")
	private Long status;

    /**
     * 修改时间
     */
	@ExcelProperty(value = "修改时间")
	@ApiModelProperty("修改时间")
	private Date updateTime;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
