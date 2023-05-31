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
 * 评论业务对象 yb_comments
 *
 * @author yibei
 * @date 2022-05-19
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("评论业务对象")
public class YbCommentsBo extends BaseEntity {

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
     * 内容id
     */
    @ApiModelProperty(value = "内容id", required = true)
    @NotNull(message = "内容id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long contentId;

    /**
     * 上级id
     */
    @ApiModelProperty(value = "上级id", required = true)
    @NotNull(message = "上级id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    @NotNull(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sourceType;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容", required = true)
    @NotBlank(message = "评论内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String comments;

    /**
     * 置顶状态
     */
    @ApiModelProperty(value = "置顶状态", required = true)
    @NotNull(message = "置顶状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer isTopping;


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
