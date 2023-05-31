package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 答题记录对象 yb_answer_log
 *
 * @author yibei
 * @date 2022-05-07
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_answer_log")
public class YbAnswerLog implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 题目id
     */
    @TableField(value = "`question_bank_content_id`")
    private Long questionBankContentId;

    /**
     * 题库id
     */
    @TableField(value = "`question_bank_id`")
    private Long questionBankId;

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
