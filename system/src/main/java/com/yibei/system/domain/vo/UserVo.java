package com.yibei.system.domain.vo;

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
 * 用户视图对象 user
 *
 * @author yibei
 * @date 2022-04-26
 */
@Data
@ApiModel("用户视图对象")
@ExcelIgnoreUnannotated
public class UserVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  id
     */
	@ExcelProperty(value = "id")
	@ApiModelProperty("id")
	private Long id;

    /**
     * 用户邮箱
     */
//	@ExcelProperty(value = "用户邮箱")
	@ApiModelProperty("用户邮箱")
	private String email;

    /**
     * 密码
     */
//	@ExcelProperty(value = "密码")
	@ApiModelProperty("密码")
	private String password;

    /**
     * 用户账号
     */
//	@ExcelProperty(value = "用户账号")
	@ApiModelProperty("用户账号")
	private String userName;

    /**
     * 用户昵称
     */
	@ExcelProperty(value = "用户昵称")
	@ApiModelProperty("用户昵称")
	private String nickName;

    /**
     * 手机号码
     */
//	@ExcelProperty(value = "手机号码")
	@ApiModelProperty("手机号码")
	private String phone;

    /**
     * 真实姓名
     */
//	@ExcelProperty(value = "真实姓名")
	@ApiModelProperty("真实姓名")
	private String realName;

    /**
     * 头像地址
     */
	@ExcelProperty(value = "头像地址")
	@ApiModelProperty("头像地址")
	private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
//    @ExcelProperty(value = "帐号状态", converter = ExcelDictConvert.class)
//    @ExcelDictFormat(dictType = "stop_status")
	@ApiModelProperty("帐号状态（0正常 1停用）")
	private Integer status;

    /**
     * 最后登录IP
     */
//	@ExcelProperty(value = "最后登录IP")
	@ApiModelProperty("最后登录IP")
	private String loginIp;

    /**
     * 最后登录时间
     */
//	@ExcelProperty(value = "最后登录时间")
	@ApiModelProperty("最后登录时间")
	private Date loginTime;

    /**
     * 登录次数
     */
//	@ExcelProperty(value = "登录次数")
	@ApiModelProperty("登录次数")
	private Long loginCount;

    /**
     * 微信
     */
//	@ExcelProperty(value = "微信")
	@ApiModelProperty("微信")
	private String weChat;

    /**
     * 微信小程序openid
     */
//	@ExcelProperty(value = "微信")
	@ApiModelProperty("微信小程序openid")
	private String appletOpenid;

	/**
	 * 会员状态 0非vip，1vip
	 */
	@ExcelProperty(value = "会员状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "vip_status")
	@ApiModelProperty("会员状态")
	private Integer vipStatus;

	/**
	 * 会员到期时间
	 */
	@ExcelProperty(value = "会员到期时间")
	@ApiModelProperty("会员到期时间")
	private Date endTime;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
