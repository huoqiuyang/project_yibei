package com.yibei.yb.domain.bo;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 会员订单业务对象 yb_order
 *
 * @author yibei
 * @date 2022-04-27
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("会员订单业务对象")
public class YbOrderBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号", required = true)
    @NotBlank(message = "订单编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderNo;

    /**
     * 订单金额
     */
    @ApiModelProperty(value = "订单金额", required = true)
    @NotNull(message = "订单金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal orderAmount;

    /**
     * 支付状态
     */
    @ApiModelProperty(value = "支付状态", required = true)
    @NotNull(message = "支付状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

    /**
     * 会员标题
     */
    @ApiModelProperty(value = "会员标题")
    private String vipTitle;

    /**
     * 会员月数
     */
    @ApiModelProperty(value = "会员月数")
    private Integer vipMonth;

    /**
     * 会员中心id
     */
    @ApiModelProperty(value = "会员中心id", required = true)
    @NotNull(message = "会员中心id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long vipId;

    /**
     * 支付次数
     */
    @ApiModelProperty(value = "支付次数")
    private Integer payCount;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remarks;


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
