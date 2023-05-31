package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class YbVipCommodityInfoVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("月数")
    private Integer month;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("单月价")
    private BigDecimal precioMensual;

}
