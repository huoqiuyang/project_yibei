package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbUserBookshelfQbInfoVo {

    @ApiModelProperty("总题数")
    private long allNum;

    @ApiModelProperty("已做题数")
    private long doneNum;

    @ApiModelProperty("完成百分比")
    private String completedPercentage;

}
