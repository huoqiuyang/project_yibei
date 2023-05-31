package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 笔记对象 yb_note
 *
 * @author yibei
 * @date 2022-05-11
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_note")
public class YbNote implements Serializable {

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
     * 词条id
     */
    @TableField(value = "`entry_id`")
    private Long entryId;

    /**
     * 用户id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 内容
     */
    @TableField(value = "`content`")
    private String content;

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
