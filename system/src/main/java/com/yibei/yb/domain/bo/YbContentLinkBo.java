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
 * 相关链接业务对象 yb_content_link
 *
 * @author yibei
 * @date 2022-05-06
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("相关链接业务对象")
public class YbContentLinkBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 来源类型
     */
    @ApiModelProperty(value = "来源类型", required = true)
    @NotNull(message = "来源类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sourceType;

    /**
     * 词条id
     */
    @ApiModelProperty(value = "词条id", required = true)
    @NotNull(message = "词条id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long entryId;

    /**
     * 链接词条来源类型
     */
    @ApiModelProperty(value = "链接词条来源类型", required = true)
    @NotNull(message = "链接词条来源类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkSourceType;

    /**
     * 链接词条id
     */
    @ApiModelProperty(value = "链接词条id", required = true)
    @NotNull(message = "链接词条id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long linkEntryId;


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
