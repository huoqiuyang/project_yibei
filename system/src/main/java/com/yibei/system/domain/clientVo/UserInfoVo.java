package com.yibei.system.domain.clientVo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserInfoVo {

    @ApiModelProperty("用户ID")
    private Long id;

    @ApiModelProperty("用户账号")
    private String userName;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("手机号码")
    private String phone;

    @ApiModelProperty("邮箱地址")
    private String email;

    @ApiModelProperty("头像地址")
    private String avatar;

    @ApiModelProperty("微信")
    private String weChat;

    @ApiModelProperty("真实姓名")
    private String realName;


    @ApiModelProperty("会员状态(0非会员1会员)")
    private int vipStatus;

    @ApiModelProperty("会员到期时间")
    private Date vipEndTime;

}
