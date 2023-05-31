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
 * 报错业务对象 yb_report_errors
 *
 * @author yibei
 * @date 2022-05-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("报错业务对象")
public class YbReportErrorsBo extends BaseEntity {

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
     * 词条id
     */
    @ApiModelProperty(value = "词条id", required = true)
    @NotNull(message = "词条id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long entryId;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容", required = true)
    @NotBlank(message = "内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String imgUrl;


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
