package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 报错对象 yb_report_errors
 *
 * @author yibei
 * @date 2022-05-18
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_report_errors")
public class YbReportErrors implements Serializable {

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
     * 词条id
     */
    @TableField(value = "`entry_id`")
    private Long entryId;

    /**
     * 内容
     */
    @TableField(value = "`content`")
    private String content;

    /**
     * 图片
     */
    @TableField(value = "`img_url`")
    private String imgUrl;

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
