package com.yibei.cms.domain.bo;

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
 * 内容分类业务对象 cms_category
 *
 * @author yibei
 * @date 2022-01-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("内容分类业务对象")
public class CmsCategoryBo extends BaseEntity {

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 父级id，可无限
     */
    @ApiModelProperty(value = "父级id，可无限", required = true)
    @NotNull(message = "父级id，可无限不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

    /**
     * 类名
     */
    @ApiModelProperty(value = "类名")
    private String className;

    /**
     * 分类代码
     */
    @ApiModelProperty(value = "分类代码")
    private String code;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String imgUrl;

    /**
     * 图片集
     */
    @ApiModelProperty(value = "图片集")
    private String imgsUrl;

    /**
     * 权重 值越大越靠前
     */
    @ApiModelProperty(value = "权重 值越大越靠前", required = true)
    @NotNull(message = "权重 值越大越靠前不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long weight;

    /**
     * 自定义属性
     */
    @ApiModelProperty(value = "自定义属性")
    private String customProperty;

    /**
     * 备用字段
     */
    @ApiModelProperty(value = "备用字段")
    private String bak;

    /**
     * 备用字段2
     */
    @ApiModelProperty(value = "备用字段2")
    private String bak2;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Long status;

    /**
     * 是否开启新增列表数据
     */
    @ApiModelProperty(value = "是否开启新增列表数据")
    private Long createDataEnable;

    /**
     * 是否开启新增子分类
     */
    @ApiModelProperty(value = "是否开启新增子分类")
    private Long createChildEnable;

    /**
     * 子分类模板id
     */
    @ApiModelProperty(value = "子分类模板id")
    private Long childTemplateId;


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
