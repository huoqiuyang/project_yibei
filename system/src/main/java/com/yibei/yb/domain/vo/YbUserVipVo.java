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
 * 用户会员视图对象 yb_user_vip
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@ApiModel("用户会员视图对象")
@ExcelIgnoreUnannotated
public class YbUserVipVo extends BaseVo {

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
     * 开始时间
     */
	@ExcelProperty(value = "开始时间")
	@ApiModelProperty("开始时间")
	private Date startTime;

    /**
     * 到期时间
     */
	@ExcelProperty(value = "到期时间")
	@ApiModelProperty("到期时间")
	private Date endTime;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
