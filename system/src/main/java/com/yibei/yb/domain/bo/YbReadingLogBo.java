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
 * 阅读记录业务对象 yb_reading_log
 *
 * @author yibei
 * @date 2022-05-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("阅读记录业务对象")
public class YbReadingLogBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 拓展阅读id
     */
    @ApiModelProperty(value = "拓展阅读id", required = true)
    @NotNull(message = "拓展阅读id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long expandReadId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 阅读状态
     */
    @ApiModelProperty(value = "阅读状态", required = true)
    @NotNull(message = "阅读状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;


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
