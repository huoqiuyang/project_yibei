package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 教材词条对象 yb_teaching_material_entry
 *
 * @author yibei
 * @date 2022-05-14
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_teaching_material_entry")
public class YbTeachingMaterialEntry implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 教材id
     */
    @TableField(value = "`teaching_material_id`")
    private Long teachingMaterialId;

    /**
     * 上级id
     */
    @TableField(value = "`parent_id`")
    private Long parentId;

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
     * 图片
     */
    @TableField(value = "`img_url`")
    private String imgUrl;

    /**
     * 重要度
     */
    @TableField(value = "`importance`")
    private Long importance;

    /**
     * 音频
     */
    @TableField(value = "`audio_frequency`")
    private String audioFrequency;

    /**
     * 相关链接
     */
    @TableField(value = "`related_links`")
    private String relatedLinks;

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
     * 关键词
     */
    @TableField(value = "`key_word`")
    private String keyWord;

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