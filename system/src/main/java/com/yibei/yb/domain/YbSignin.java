package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 签到对象 yb_signin
 *
 * @author yibei
 * @date 2022-05-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_signin")
public class YbSignin implements Serializable {

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
     * 日期
     */
    @TableField(value = "`date_day`")
    private String dateDay;

    /**
     * 连续签到天数
     */
    @TableField(value = "`continuity_day`")
    private Long continuityDay;

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
