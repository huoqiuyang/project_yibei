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

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 教材阅读背诵记录业务对象 yb_teaching_material_log
 *
 * @author yibei
 * @date 2022-05-12
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("教材阅读背诵记录业务对象")
public class YbTeachingMaterialLogBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 教材id
     */
    @ApiModelProperty(value = "教材id", required = true)
    @NotNull(message = "教材id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teachingMaterialId;

    /**
     * 词条id
     */
    @ApiModelProperty(value = "词条id", required = true)
    @NotNull(message = "词条id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teachingMaterialEntryId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 学习类型
     */
    @ApiModelProperty(value = "学习类型", required = true)
    @NotNull(message = "学习类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer learningType;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer status;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间", required = true)
    @NotNull(message = "修改时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date lastTime;


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
