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
 * 兑换记录视图对象 yb_code_exchange
 *
 * @author yibei
 * @date 2022-05-26
 */
@Data
@ApiModel("兑换记录视图对象")
@ExcelIgnoreUnannotated
public class YbCodeExchangeVo extends BaseVo {

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
     * 兑换码id
     */
	@ExcelProperty(value = "兑换码id")
	@ApiModelProperty("兑换码id")
	private Long codeId;

    /**
     * 兑换码
     */
	@ExcelProperty(value = "兑换码")
	@ApiModelProperty("兑换码")
	private String code;

	/**
	 * 会员天数
	 */
	@ExcelProperty(value = "会员天数")
	@ApiModelProperty("会员天数")
	private Integer vipDay;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
