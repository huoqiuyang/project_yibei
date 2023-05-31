package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 用户会员对象 yb_user_vip
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_user_vip")
public class YbUserVip implements Serializable {

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
     * 开始时间
     */
    @TableField(value = "`start_time`")
    private Date startTime;

    /**
     * 到期时间
     */
    @TableField(value = "`end_time`")
    private Date endTime;

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
