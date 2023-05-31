package com.yibei.yb.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.yibei.common.core.domain.BaseEntity;
import com.yibei.common.core.domain.TreeEntity;
import com.yibei.common.core.validate.AddGroup;
import com.yibei.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.math.BigDecimal;

/**
 * 会员中心业务对象 yb_vip_commodity
 *
 * @author yibei
 * @date 2022-04-27
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("会员中心业务对象")
public class YbVipCommodityBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签")
    private String label;

    /**
     * 月数
     */
    @ApiModelProperty("月数")
    private Integer month;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格", required = true)
    @NotNull(message = "价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal price;

    /**
     * 单月价
     */
    @ApiModelProperty(value = "单月价")
    private BigDecimal precioMensual;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sort;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;


    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize=20;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNum=1;

    /**
     * 排序列
     */
    @ApiModelProperty("排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

}
