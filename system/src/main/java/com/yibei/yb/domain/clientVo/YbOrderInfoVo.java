package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class YbOrderInfoVo {

    @ApiModelProperty("支付方式（1兑换码兑换，2订单支付）")
    private Integer payType;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("购买信息")
    private String vipTitle;

    @ApiModelProperty("支付状态（0未支付，1已支付）")
    private Integer payStatus;

    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    @ApiModelProperty("实付金额")
    private BigDecimal payAmount;

    @ApiModelProperty("订单编号")
    private String orderNo;

    @ApiModelProperty("支付流水号")
    private String tradeNo;

    @ApiModelProperty("订单创建时间")
    private Date createTime;

    @ApiModelProperty("付款时间")
    private Date payTime;

}
