package com.yibei.system.domain.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


/**
 * 【请填写功能名称】编辑对象 city_info
 *
 * @author frame
 * @date 2021-07-14
 */
@Data
@ApiModel("【请填写功能名称】编辑对象")
public class CityInfoEditBo {

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private Integer id;

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    @NotNull(message = "$column.columnComment不能为空")
    private Integer parentId;

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    private String name;

    /** 区域代码 */
    @ApiModelProperty("区域代码")
    private String zipcode;

    /** 地区级别 1省 2市 3区 */
    @ApiModelProperty("地区级别 1省 2市 3区")
    @NotNull(message = "地区级别 1省 2市 3区不能为空")
    private Integer regionLevel;

    /** 0未开通 1已开通 */
    @ApiModelProperty("0未开通 1已开通")
    @NotNull(message = "0未开通 1已开通不能为空")
    private Integer state;

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    @NotNull(message = "$column.columnComment不能为空")
    private Integer isDeleted;
}
