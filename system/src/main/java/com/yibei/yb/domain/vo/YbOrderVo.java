package com.yibei.yb.domain.vo;

import java.math.BigDecimal;
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
 * 会员订单视图对象 yb_order
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@ApiModel("会员订单视图对象")
@ExcelIgnoreUnannotated
public class YbOrderVo extends BaseVo {

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
     * 订单编号
     */
	@ExcelProperty(value = "订单编号")
	@ApiModelProperty("订单编号")
	private String orderNo;

    /**
     * 订单金额
     */
	@ExcelProperty(value = "订单金额")
	@ApiModelProperty("订单金额")
	private BigDecimal orderAmount;

    /**
     * 支付状态
     */
    @ExcelProperty(value = "支付状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "pay_status")
	@ApiModelProperty("支付状态")
	private Integer status;

    /**
     * 会员标题
     */
	@ExcelProperty(value = "会员标题")
	@ApiModelProperty("会员标题")
	private String vipTitle;

    /**
     * 会员月数
     */
	@ExcelProperty(value = "会员月数", converter = ExcelDictConvert.class)
	@ExcelDictFormat(dictType = "month")
	@ApiModelProperty("会员月数")
	private Integer vipMonth;

    /**
     * 会员中心id
     */
	@ExcelProperty(value = "会员中心id")
	@ApiModelProperty("会员中心id")
	private Long vipId;

	/**
	 * 支付次数
	 */
	@ApiModelProperty(value = "支付次数")
	private Integer payCount;

	/**
     * 备注
     */
	@ExcelProperty(value = "备注")
	@ApiModelProperty("备注")
	private String remarks;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
