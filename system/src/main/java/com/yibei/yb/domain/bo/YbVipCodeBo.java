package com.yibei.yb.domain.bo;

import com.yibei.common.core.domain.BaseEntity;
import com.yibei.common.core.domain.TreeEntity;
import com.yibei.common.core.validate.AddGroup;
import com.yibei.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 会员兑换码业务对象 yb_vip_code
 *
 * @author yibei
 * @date 2022-04-27
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("会员兑换码业务对象")
public class YbVipCodeBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 兑换码
     */
    @ApiModelProperty(value = "兑换码")
    private String code;

    /**
     * 会员天数
     */
    @ApiModelProperty(value = "会员天数", required = true)
    @NotNull(message = "会员天数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer vipDay;

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
