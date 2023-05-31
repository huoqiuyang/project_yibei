package com.yibei.yb.domain.vo;

import java.math.BigDecimal;
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
 * 支付记录视图对象 yb_order_pay
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@ApiModel("支付记录视图对象")
@ExcelIgnoreUnannotated
public class YbOrderPayVo extends BaseVo {

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
     * 支付时间
     */
	@ExcelProperty(value = "支付时间")
	@ApiModelProperty("支付时间")
	private Date payTime;

    /**
     * 支付金额
     */
	@ExcelProperty(value = "支付金额")
	@ApiModelProperty("支付金额")
	private BigDecimal payAmount;

    /**
     * 在线支付交易流水
     */
	@ExcelProperty(value = "在线支付交易流水")
	@ApiModelProperty("在线支付交易流水")
	private String tradeNo;

    /**
     * 支付响应数据
     */
	@ExcelProperty(value = "支付响应数据")
	@ApiModelProperty("支付响应数据")
	private String responseData;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
