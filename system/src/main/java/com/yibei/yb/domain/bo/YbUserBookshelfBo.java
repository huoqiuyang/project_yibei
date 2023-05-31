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
 * 用户书架业务对象 yb_user_bookshelf
 *
 * @author yibei
 * @date 2022-05-06
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("用户书架业务对象")
public class YbUserBookshelfBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 教材题库类型
     */
    @ApiModelProperty(value = "教材题库类型", required = true)
    @NotNull(message = "教材题库类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long contentType;

    /**
     * 内容id
     */
    @ApiModelProperty(value = "内容id", required = true)
    @NotNull(message = "内容id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long contentId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 最近打开时间
     */
    @ApiModelProperty(value = "最近打开时间", required = true)
    @NotNull(message = "最近打开时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date lastOpenTime;


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