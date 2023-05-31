package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ReportSigninInfoVo {

    @ApiModelProperty("日期（yyyy-MM-dd）")
    private String date;

    @ApiModelProperty("天数（dd）")
    private Integer day;

    @ApiModelProperty("是否签到打卡（0未打卡，1已打卡）")
    private int isSignin;

    @ApiModelProperty("学习时间（分钟）")
    private int studyMin;

}
