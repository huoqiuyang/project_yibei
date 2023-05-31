package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudyInfoVo {

    @ApiModelProperty("每天学习数量")
    private int studyNum;

    @ApiModelProperty("每天复习数量")
    private int reviewNum;

    @ApiModelProperty("每天学习+复习数量")
    private int allQuantity;

    @ApiModelProperty("共需天数")
    private int day;

    @ApiModelProperty("是否开启语音播放（0关闭1开启）")
    private Integer isVoice = 0;

}
