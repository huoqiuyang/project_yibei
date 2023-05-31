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
 * 学习时间记录视图对象 yb_study_time_log
 *
 * @author yibei
 * @date 2022-05-18
 */
@Data
@ApiModel("学习时间记录视图对象")
@ExcelIgnoreUnannotated
public class YbStudyTimeLogVo extends BaseVo {

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
     * 类型
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "study_time_type")
	@ApiModelProperty("类型")
	private Integer type;

    /**
     * 内容id
     */
	@ExcelProperty(value = "内容id")
	@ApiModelProperty("内容id")
	private Long contentId;

    /**
     * 累计分钟数
     */
	@ExcelProperty(value = "累计分钟数")
	@ApiModelProperty("累计分钟数")
	private Long min;

    /**
     * 日期
     */
	@ExcelProperty(value = "日期")
	@ApiModelProperty("日期")
	private String dateDay;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
