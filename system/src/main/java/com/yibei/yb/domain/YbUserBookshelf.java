package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 用户书架对象 yb_user_bookshelf
 *
 * @author yibei
 * @date 2022-05-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_user_bookshelf")
public class YbUserBookshelf implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 教材题库类型
     */
    @TableField(value = "`content_type`")
    private Long contentType;

    /**
     * 内容id
     */
    @TableField(value = "`content_id`")
    private Long contentId;

    /**
     * 用户id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 最近打开时间
     */
    @TableField(value = "`last_open_time`")
    private Date lastOpenTime;

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
