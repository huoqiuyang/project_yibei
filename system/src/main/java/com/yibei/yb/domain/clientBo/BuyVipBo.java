package com.yibei.yb.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BuyVipBo {

    @ApiModelProperty("会员中心id")
    @NotNull(message = "会员中心id不能为空")
    private Long vipCommodityId;

    @ApiModelProperty("备注")
    private String remarks;
}
