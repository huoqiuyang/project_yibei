package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 学习时间记录对象 yb_study_time_log
 *
 * @author yibei
 * @date 2022-05-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_study_time_log")
public class YbStudyTimeLog implements Serializable {

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
     * 类型
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 内容id
     */
    @TableField(value = "`content_id`")
    private Long contentId;

    /**
     * 累计分钟数
     */
    @TableField(value = "`min`")
    private Long min;

    /**
     * 日期
     */
    @TableField(value = "`date_day`")
    private String dateDay;

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
