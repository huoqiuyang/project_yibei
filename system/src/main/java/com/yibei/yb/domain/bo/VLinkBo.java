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
 * 相关链接业务对象 v_link
 *
 * @author yibei
 * @date 2022-06-09
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("相关链接业务对象")
public class VLinkBo extends BaseEntity {

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    @NotBlank(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 书籍ID
     */
    @ApiModelProperty(value = "书籍ID", required = true)
    @NotNull(message = "书籍ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long bookId;

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
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String imgUrl;

    /**
     * 重要度
     */
    @ApiModelProperty(value = "重要度", required = true)
    @NotBlank(message = "重要度不能为空", groups = { AddGroup.class, EditGroup.class })
    private String importance;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sort;

    /**
     * 书籍名称
     */
    @ApiModelProperty(value = "书籍名称")
    private String bookTitle;


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
