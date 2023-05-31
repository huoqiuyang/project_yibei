package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 题库对象 yb_question_bank
 *
 * @author yibei
 * @date 2022-05-06
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_question_bank")
public class YbQuestionBank implements Serializable {

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
     * 展示图
     */
    @TableField(value = "`img_url`")
    private String imgUrl;

    /**
     * 描述
     */
    @TableField(value = "`describe`")
    private String describe;

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
