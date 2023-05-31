package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 兑换记录对象 yb_code_exchange
 *
 * @author yibei
 * @date 2022-05-26
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_code_exchange")
public class YbCodeExchange implements Serializable {

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
     * 兑换码id
     */
    @TableField(value = "`code_id`")
    private Long codeId;

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
