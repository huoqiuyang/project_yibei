package com.yibei.system.domain.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * 【请填写功能名称】添加对象 city_region
 *
 * @author frame
 * @date 2021-07-14
 */
@Data
@ApiModel("【请填写功能名称】添加对象")
public class CityRegionAddBo {

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    @NotNull(message = "$column.columnComment不能为空")
    private Integer cityId;

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    @NotBlank(message = "$column.columnComment不能为空")
    private String name;

    /** 排序 */
    @ApiModelProperty("排序")
    @NotNull(message = "排序不能为空")
    private Integer sort;

    /** 0未开通 1已开通 */
    @ApiModelProperty("0未开通 1已开通")
    @NotNull(message = "0未开通 1已开通不能为空")
    private Integer state;

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    @NotNull(message = "$column.columnComment不能为空")
    private Integer isDeleted;

    /** $column.columnComment */
    @ApiModelProperty("$column.columnComment")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "$column.columnComment不能为空")
    private LocalDateTime createTime;
}
