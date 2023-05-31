package com.yibei.cms.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 内容分类对象 cms_category
 *
 * @author yibei
 * @date 2022-01-08
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cms_category")
public class CmsCategory implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * id
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 父级id，可无限
     */
    @TableField(value = "`parent_id`")
    private Long parentId;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 类名
     */
    @TableField(value = "`class_name`")
    private String className;

    /**
     * 分类代码
     */
    @TableField(value = "`code`")
    private String code;

    /**
     * 图片
     */
    @TableField(value = "`img_url`")
    private String imgUrl;

    /**
     * 图片集
     */
    @TableField(value = "`imgs_url`")
    private String imgsUrl;

    /**
     * 权重 值越大越靠前
     */
    @TableField(value = "`weight`")
    private Long weight;

    /**
     * 自定义属性
     */
    @TableField(value = "`custom_property`")
    private String customProperty;

    /**
     * 备用字段
     */
    @TableField(value = "`bak`")
    private String bak;

    /**
     * 备用字段2
     */
    @TableField(value = "`bak2`")
    private String bak2;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    private Long status;

    /**
     * 是否开启新增列表数据
     */
    @TableField(value = "`create_data_enable`")
    private Long createDataEnable;

    /**
     * 是否开启新增子分类
     */
    @TableField(value = "`create_child_enable`")
    private Long createChildEnable;

    /**
     * 子分类模板id
     */
    @TableField(value = "`child_template_id`")
    private Long childTemplateId;

    /**
     * 是否删除 0.否 1.是
     */
    @TableField(value = "`is_deleted`")
    private Long isDeleted;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
}
