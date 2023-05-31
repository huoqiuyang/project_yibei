package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 相关链接对象 yb_content_link
 *
 * @author yibei
 * @date 2022-05-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_content_link")
public class YbContentLink implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 来源类型
     */
    @TableField(value = "`source_type`")
    private Long sourceType;

    /**
     * 词条id
     */
    @TableField(value = "`entry_id`")
    private Long entryId;

    /**
     * 链接词条来源类型
     */
    @TableField(value = "`link_source_type`")
    private Long linkSourceType;

    /**
     * 链接词条id
     */
    @TableField(value = "`link_entry_id`")
    private Long linkEntryId;

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
