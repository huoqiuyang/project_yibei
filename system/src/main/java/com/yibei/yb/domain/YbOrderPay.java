package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 支付记录对象 yb_order_pay
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_order_pay")
public class YbOrderPay implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 用户id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 订单编号
     */
    @TableField(value = "`order_no`")
    private String orderNo;

    /**
     * 支付时间
     */
    @TableField(value = "`pay_time`")
    private Date payTime;

    /**
     * 支付金额
     */
    @TableField(value = "`pay_amount`")
    private BigDecimal payAmount;

    /**
     * 在线支付交易流水
     */
    @TableField(value = "`trade_no`")
    private String tradeNo;

    /**
     * 支付响应数据
     */
    @TableField(value = "`response_data`")
    private String responseData;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 删除 0否 1是
     */
    @TableField(value = "`is_deleted`")
    private Integer isDeleted;
}
