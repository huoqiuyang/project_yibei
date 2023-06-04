package com.yibei.yb.domain.clientVo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbTmeCatalogueSecondInfoVo {

    @ApiModelProperty(value = "ID", required = true)
    private Long id;

    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("重要度")
    private Integer importance;

    @ApiModelProperty(value = "音频url", required = true)
    private String audio;

    @JsonIgnore
    private Integer order;

    @ApiModelProperty("是否加入背诵：0未加入1已加入（制定播单接口中这个代表是否加入播单）")
    private int type;

}
