package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 题库内容对象 yb_question_bank_content
 *
 * @author yibei
 * @date 2022-05-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_question_bank_content")
public class YbQuestionBankContent implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 题库id
     */
    @TableField(value = "`question_bank_id`")
    private Long questionBankId;

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
     * 答案
     */
    @TableField(value = "`answer`")
    private String answer;

    /**
     * 图片
     */
    @TableField(value = "`img_url`")
    private String imgUrl;

    /**
     * 标签
     */
    @TableField(value = "`label`")
    private String label;

    /**
     * 重要度
     */
    @TableField(value = "`importance`")
    private Integer importance;

    /**
     * 相关链接
     */
    @TableField(value = "`related_links`")
    private String relatedLinks;

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
