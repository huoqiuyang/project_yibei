package com.yibei.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * OSS云存储对象
 *
 * @author Lion Li
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_oss")
public class SysOss implements Serializable {

	private static final long serialVersionUID = 1L;


	/**
	 * 云存储主键
	 */
	@TableId(value = "oss_id", type = IdType.AUTO)
	private Long ossId;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 原名
	 */
	private String originalName;

	/**
	 * 文件后缀名
	 */
	private String fileSuffix;

	/**
	 * URL地址
	 */
	private String url;

	/**
	 * 前端用户id
	 */
	private Long userId;

	/**
	 * 文件分类
	 */
	private String cate;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 上传人
	 */
	@TableField(fill = FieldFill.INSERT)
	private String createBy;

	/**
	 * 更新时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;

	/**
	 * 更新人
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateBy;

	/**
	 * 服务商
	 */
	private String service;

}
