package com.yibei.system.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yibei.system.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户订阅视图对象 user_subscribe
 *
 * @author dde
 * @date 2021-10-25
 */
@Data
@ApiModel("用户订阅视图对象")
@ExcelIgnoreUnannotated
public class SubscribeVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 内容id
     */
	@ExcelProperty(value = "内容id")
	@ApiModelProperty("内容id")
	private Long contentId;

	/**
	 * 内容类型（1：新闻，2：DDE服务项目）
	 */
	@ExcelProperty(value = "内容类型")
	@ApiModelProperty("内容类型")
	private Integer contentType;

    /**
     * 新闻id
     */
	@ExcelProperty(value = "新闻id")
	@ApiModelProperty("新闻id")
	private Long newsId;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
