package com.yibei.yb.domain.clientVo;

import com.yibei.system.domain.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SignStudyTimeInfoVo {

    @ApiModelProperty("坚持打卡天数")
    private int insistSignDay;

    @ApiModelProperty("连续打卡天数")
    private int continuitySignDay;

    @ApiModelProperty("当日学习时间（分钟）")
    private int toDayStudyMin;

    @ApiModelProperty("超越用户百分比")
    private String transcendPercentage;

    @ApiModelProperty("用户信息")
    private User user;

}
