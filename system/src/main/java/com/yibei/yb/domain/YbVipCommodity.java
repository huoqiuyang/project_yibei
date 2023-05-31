package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 会员中心对象 yb_vip_commodity
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_vip_commodity")
public class YbVipCommodity implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 标题
     */
    @TableField(value = "`title`")
    private String title;

    /**
     * 标签
     */
    @TableField(value = "`label`")
    private String label;

    /**
     * 月数
     */
    @TableField(value = "`month`")
    private Integer month;

    /**
     * 价格
     */
    @TableField(value = "`price`")
    private BigDecimal price;

    /**
     * 单月价
     */
    @TableField(value = "`precio_mensual`")
    private BigDecimal precioMensual;

    /**
     * 排序
     */
    @TableField(value = "`sort`")
    private Long sort;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    private Integer status;

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
