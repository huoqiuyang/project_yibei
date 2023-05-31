package com.yibei.yb.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TreeCityListVo {

    @ApiModelProperty("ID")
    private Long id;

    @ExcelProperty(value = "名称")
    @ApiModelProperty("名称")
    private String name;

    @ExcelProperty(value = "首字母")
    @ApiModelProperty("首字母")
    private String letter;

    @ExcelProperty(value = "状态")
    @ApiModelProperty("状态")
    private Integer status;

    @ExcelProperty(value = "父级id")
    @ApiModelProperty("父级id")
    private Long parentId;

    @ApiModelProperty("子集列表")
    private List<TreeCityListVo> list;


}
