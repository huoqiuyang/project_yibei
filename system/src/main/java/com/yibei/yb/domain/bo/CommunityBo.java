package com.yibei.yb.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CommunityBo {

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("区域")
    private String region;

}
