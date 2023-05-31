package com.yibei.system.domain.vo;

import com.yibei.system.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* 【请填写功能名称】视图对象 mall_package
*
* @author frame
* @date 2021-07-14
*/
@Data
@ApiModel("【请填写功能名称】视图对象")
public class CityRegionVo extends BaseVo {
private static final long serialVersionUID = 1L;

    @ApiModelProperty("$column.columnComment")
    private Integer cityId;

    @ApiModelProperty("$column.columnComment")
    private String name;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("0未开通 1已开通")
    private Integer state;

    @ApiModelProperty("$column.columnComment")
    private Integer isDeleted;
}
