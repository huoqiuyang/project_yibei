package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbTmeCatalogueSecondInfoVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("重要度")
    private Integer importance;

    @ApiModelProperty("是否加入背诵：0未加入1已加入")
    private int type;

}
