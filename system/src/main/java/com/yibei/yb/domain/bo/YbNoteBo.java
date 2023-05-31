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
 * 笔记业务对象 yb_note
 *
 * @author yibei
 * @date 2022-05-11
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("笔记业务对象")
public class YbNoteBo extends BaseEntity {

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
    private Long entryId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", required = true)
    @NotBlank(message = "内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;


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
