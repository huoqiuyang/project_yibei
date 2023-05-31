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
 * 题库内容业务对象 yb_question_bank_content
 *
 * @author yibei
 * @date 2022-05-06
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("题库内容业务对象")
public class YbQuestionBankContentBo extends TreeEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 题库id
     */
    @ApiModelProperty(value = "题库id", required = true)
    @NotNull(message = "题库id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long questionBankId;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 答案
     */
    @ApiModelProperty(value = "答案", required = true)
//    @NotBlank(message = "答案不能为空", groups = { AddGroup.class, EditGroup.class })
    private String answer;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String imgUrl;

    /**
     * 标签
     */
    @ApiModelProperty(value = "标签", required = true)
//    @NotBlank(message = "标签不能为空", groups = { AddGroup.class, EditGroup.class })
    private String label;

    /**
     * 重要度
     */
    @ApiModelProperty(value = "重要度")
    private Integer importance;

    /**
     * 相关链接
     */
    @ApiModelProperty(value = "相关链接")
    private String relatedLinks;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = true)
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sort;

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
