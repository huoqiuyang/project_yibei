package com.yibei.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yibei.common.annotation.ExcelDictFormat;
import com.yibei.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 用户登录日志视图对象 user_login_log
 *
 * @author yibei
 * @date 2021-09-26
 */
@Data
@ApiModel("用户登录日志视图对象")
@ExcelIgnoreUnannotated
public class UserLoginLogVo {

	private static final long serialVersionUID = 1L;

	/**
     *  id
     */
	@ApiModelProperty("id")
	private Long id;

    /**
     * 用户名
     */
	@ExcelProperty(value = "用户名")
	@ApiModelProperty("用户名")
	private String username;

    /**
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 状态 0.失败 1.成功
     */
	@ExcelProperty(value = "状态 0.失败 1.成功")
	@ApiModelProperty("状态 0.失败 1.成功")
	private Integer sttus;

    /**
     * 登录IP
     */
	@ExcelProperty(value = "登录IP")
	@ApiModelProperty("登录IP")
	private String loginIp;

    /**
     * 备注
     */
	@ExcelProperty(value = "备注")
	@ApiModelProperty("备注")
	private String remark;

    /**
     * 登录来源页面url
     */
	@ExcelProperty(value = "登录来源页面url")
	@ApiModelProperty("登录来源页面url")
	private String refUrl;

    /**
     * 浏览器ua
     */
	@ExcelProperty(value = "浏览器ua")
	@ApiModelProperty("浏览器ua")
	private String userAgent;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
	@ExcelProperty(value = "删除标志", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=代表存在,1=代表删除")
	@ApiModelProperty("删除标志（0代表存在 1代表删除）")
	private Integer isDeleted;


}
