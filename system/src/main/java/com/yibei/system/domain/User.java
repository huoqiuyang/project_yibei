package com.yibei.system.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 用户对象 user
 *
 * @author yibei
 * @date 2022-04-26
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * id
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 用户邮箱
     */
    @TableField(value = "`email`")
    private String email;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;

    /**
     * 用户账号
     */
    @TableField(value = "`user_name`")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField(value = "`nick_name`")
    private String nickName;

    /**
     * 手机号码
     */
    @TableField(value = "`phone`")
    private String phone;

    /**
     * 真实姓名
     */
    @TableField(value = "`real_name`")
    private String realName;

    /**
     * 头像地址
     */
    @TableField(value = "`avatar`")
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 最后登录IP
     */
    @TableField(value = "`login_ip`")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @TableField(value = "`login_time`")
    private Date loginTime;

    /**
     * 登录次数
     */
    @TableField(value = "`login_count`")
    private Long loginCount;

    /**
     * 微信
     */
    @TableField(value = "`we_chat`")
    private String weChat;

    /**
     * 微信小程序openid
     */
    @TableField(value = "`applet_openid`")
    private String appletOpenid;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField(value = "`is_deleted`")
    private Integer isDeleted;
}
