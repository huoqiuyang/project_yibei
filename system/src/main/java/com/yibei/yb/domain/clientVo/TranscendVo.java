package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TranscendVo {

    @ApiModelProperty("当日学习过的总用户数")
    private long counts;

    @ApiModelProperty("低于当前用户当天学习时间的用户数")
    private long transcend;

}
