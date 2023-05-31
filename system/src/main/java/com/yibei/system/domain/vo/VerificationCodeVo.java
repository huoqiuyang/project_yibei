package com.yibei.system.domain.vo;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yibei.system.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 验证码记录视图对象 verification_code
 *
 * @author yibei
 * @date 2021-10-24
 */
@Data
@ApiModel("验证码记录视图对象")
@ExcelIgnoreUnannotated
public class VerificationCodeVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 账号
     */
	@ExcelProperty(value = "账号")
	@ApiModelProperty("账号")
	private String account;

    /**
     * 验证码
     */
	@ExcelProperty(value = "验证码")
	@ApiModelProperty("验证码")
	private String code;

    /**
     * 验证码类型
     */
	@ExcelProperty(value = "验证码类型")
	@ApiModelProperty("验证码类型")
	private Long type;

    /**
     * 状态
     */
	@ExcelProperty(value = "状态")
	@ApiModelProperty("状态")
	private Long status;

    /**
     * 失效时间
     */
	@ExcelProperty(value = "失效时间")
	@ApiModelProperty("失效时间")
	private Date invalidTime;

    /**
     * 删除标志
     */
	@ExcelProperty(value = "删除标志")
	@ApiModelProperty("删除标志")
	private Integer isDeleted;


}
