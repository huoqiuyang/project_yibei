package com.yibei.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户登录日志对象 user_login_log
 *
 * @author yibei
 * @date 2021-09-26
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_login_log")
public class UserLoginLog implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 状态 0.失败 1.成功
     */
    private Integer status;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 备注
     */
    private String remark;

    /**
     * 登录来源页面url
     */
    private String refUrl;

    /**
     * 浏览器ua
     */
    private String userAgent;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private Integer isDeleted;

}
