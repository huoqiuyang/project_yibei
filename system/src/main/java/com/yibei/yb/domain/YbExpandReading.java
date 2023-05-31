package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 拓展阅读对象 yb_expand_reading
 *
 * @author yibei
 * @date 2022-05-11
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_expand_reading")
public class YbExpandReading implements Serializable {

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
     * 描述
     */
    @TableField(value = "`describe`")
    private String describe;

    /**
     * 标签
     */
    @TableField(value = "`label`")
    private String label;

    /**
     * 展示图
     */
    @TableField(value = "`img_url`")
    private String imgUrl;

    /**
     * 音频
     */
    @TableField(value = "`audio_frequency`")
    private String audioFrequency;

    /**
     * 内容
     */
    @TableField(value = "`content`")
    private String content;

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
