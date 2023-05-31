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
 * 签到视图对象 yb_signin
 *
 * @author yibei
 * @date 2022-05-18
 */
@Data
@ApiModel("签到视图对象")
@ExcelIgnoreUnannotated
public class YbSigninVo extends BaseVo {

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
     * 日期
     */
	@ExcelProperty(value = "日期")
	@ApiModelProperty("日期")
	private String dateDay;

    /**
     * 连续签到天数
     */
	@ExcelProperty(value = "连续签到天数")
	@ApiModelProperty("连续签到天数")
	private Long continuityDay;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
