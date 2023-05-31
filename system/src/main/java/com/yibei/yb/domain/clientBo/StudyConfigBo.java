package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudyConfigBo {

    @ApiModelProperty("教材id")
    @NotNull
    private Long id;

    @ApiModelProperty("每天学习数量")
    @NotNull
    private Long quantity;

    @ApiModelProperty("是否开启语音播放(0:关闭，1开启)")
    private Integer isVoice;

}
