package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 教材阅读背诵记录对象 yb_teaching_material_log
 *
 * @author yibei
 * @date 2022-05-12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_teaching_material_log")
public class YbTeachingMaterialLog implements Serializable {

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
    @TableField(value = "`teaching_material_entry_id`")
    private Long teachingMaterialEntryId;

    /**
     * 用户id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 学习类型
     */
    @TableField(value = "`learning_type`")
    private Integer learningType;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 修改时间
     */
    @TableField(value = "`last_time`")
    private Date lastTime;

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
