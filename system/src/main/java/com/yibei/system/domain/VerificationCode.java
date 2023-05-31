package com.yibei.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 验证码记录对象 verification_code
 *
 * @author yibei
 * @date 2021-10-24
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("verification_code")
public class VerificationCode implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 账号
     */
    @TableField(value = "`account`")
    private String account;

    /**
     * 验证码
     */
    @TableField(value = "`code`")
    private String code;

    /**
     * 验证码类型
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 失效时间
     */
    @TableField(value = "`invalid_time`")
    private LocalDateTime invalidTime;

    /**
     * 发送时间
     */
    @TableField(value = "`create_time`")
    private LocalDateTime createTime;

    /**
     * 删除标志
     */
    @TableField(value = "`is_deleted`")
    private Integer isDeleted;
}
