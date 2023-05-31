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
 * 教材阅读背诵记录视图对象 yb_teaching_material_log
 *
 * @author yibei
 * @date 2022-05-12
 */
@Data
@ApiModel("教材阅读背诵记录视图对象")
@ExcelIgnoreUnannotated
public class YbTeachingMaterialLogVo extends BaseVo {

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
	private Long teachingMaterialEntryId;

    /**
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 学习类型
     */
    @ExcelProperty(value = "学习类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "learning_type")
	@ApiModelProperty("学习类型")
	private Integer learningType;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "recite_status")
	@ApiModelProperty("状态")
	private Integer status;

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
