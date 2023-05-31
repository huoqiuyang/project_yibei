package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 会员订单对象 yb_order
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_order")
public class YbOrder implements Serializable {

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
     * 订单金额
     */
    @TableField(value = "`order_amount`")
    private BigDecimal orderAmount;

    /**
     * 支付状态
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 会员标题
     */
    @TableField(value = "`vip_title`")
    private String vipTitle;

    /**
     * 会员月数
     */
    @TableField(value = "`vip_month`")
    private Integer vipMonth;

    /**
     * 会员中心id
     */
    @TableField(value = "`vip_id`")
    private Long vipId;

    /**
     * 支付次数
     */
    @TableField(value = "`pay_count`")
    private Integer payCount;

    /**
     * 备注
     */
    @TableField(value = "`remarks`")
    private String remarks;

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
