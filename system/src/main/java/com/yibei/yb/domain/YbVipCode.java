package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 会员兑换码对象 yb_vip_code
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_vip_code")
public class YbVipCode implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 兑换码
     */
    @TableField(value = "`code`")
    private String code;

    /**
     * 会员天数
     */
    @TableField(value = "`vip_day`")
    private Integer vipDay;

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
