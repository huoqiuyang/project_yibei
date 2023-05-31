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

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 文章业务对象 cms_article
 *
 * @author yibei
 * @date 2022-01-08
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("文章业务对象")
public class CmsArticleBo extends BaseEntity {

    /**
     * id
     */
    @ApiModelProperty(value = "id", required = true)
    @NotNull(message = "id不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id", required = true)
    @NotNull(message = "分类id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long categoryId;

    /**
     * 类型路径
     */
    @ApiModelProperty(value = "类型路径")
    private String categoryPath;

    /**
     * 标识代码
     */
    @ApiModelProperty(value = "标识代码")
    private String code;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 副标题
     */
    @ApiModelProperty(value = "副标题")
    private String subTitle;

    /**
     * 备用标题
     */
    @ApiModelProperty(value = "备用标题")
    private String bakTitle;

    /**
     * 描述、摘要
     */
    @ApiModelProperty(value = "描述、摘要")
    private String description;

    /**
     * 备用描述
     */
    @ApiModelProperty(value = "备用描述")
    private String bakDescription;

    /**
     * 外链接
     */
    @ApiModelProperty(value = "外链接")
    private String linkUrl;

    /**
     * 备用外链接
     */
    @ApiModelProperty(value = "备用外链接")
    private String subLinkUrl;

    /**
     * 封面图
     */
    @ApiModelProperty(value = "封面图")
    private String imgUrl;

    /**
     * 相册，用英文逗号隔开
     */
    @ApiModelProperty(value = "相册，用英文逗号隔开")
    private String imgsUrl;

    /**
     * 视频链接
     */
    @ApiModelProperty(value = "视频链接")
    private String videoUrl;

    /**
     * 附件
     */
    @ApiModelProperty(value = "附件")
    private String attachment;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String content;

    /**
     * 内容备用
     */
    @ApiModelProperty(value = "内容备用")
    private String contentBak;

    /**
     * seo标题
     */
    @ApiModelProperty(value = "seo标题")
    private String metaTitle;

    /**
     * seo描述
     */
    @ApiModelProperty(value = "seo描述")
    private String metaDescription;

    /**
     * seo关键字
     */
    @ApiModelProperty(value = "seo关键字")
    private String metaKeywords;

    /**
     * seo标题 备用字段
     */
    @ApiModelProperty(value = "seo标题 备用字段")
    private String metaTitleBak;

    /**
     * seo描述 备用字段
     */
    @ApiModelProperty(value = "seo描述 备用字段")
    private String metaDescriptionBak;

    /**
     * seo关键字 备用字段
     */
    @ApiModelProperty(value = "seo关键字 备用字段")
    private String metaKeywordsBak;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 文章来源
     */
    @ApiModelProperty(value = "文章来源")
    private String source;

    /**
     * 发布日期
     */
    @ApiModelProperty(value = "发布日期")
    private Date publishTime;

    /**
     * 点击量
     */
    @ApiModelProperty(value = "点击量")
    private Long hitCount;

    /**
     * 排序 越小越靠前
     */
    @ApiModelProperty(value = "排序 越小越靠前")
    private Long sort;

    /**
     * 权重 越大越靠前
     */
    @ApiModelProperty(value = "权重 越大越靠前")
    private Long weight;

    /**
     * 置顶
     */
    @ApiModelProperty(value = "置顶", required = true)
    @NotNull(message = "置顶不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long isTop;

    /**
     * 推荐
     */
    @ApiModelProperty(value = "推荐", required = true)
    @NotNull(message = "推荐不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long isRecommend;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 外键备用字段
     */
    @ApiModelProperty(value = "外键备用字段")
    private String fkBak1;

    /**
     * 外键备用字段
     */
    @ApiModelProperty(value = "外键备用字段")
    private String fkBak2;

    /**
     * 外键备用字段
     */
    @ApiModelProperty(value = "外键备用字段")
    private String fkBak3;

    /**
     * 备用字段
     */
    @ApiModelProperty(value = "备用字段")
    private String bak1;

    /**
     * 备用字段
     */
    @ApiModelProperty(value = "备用字段")
    private String bak2;

    /**
     * 备用字段
     */
    @ApiModelProperty(value = "备用字段")
    private String bak3;


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

    /**
     * 关键词
     */
    @ApiModelProperty(value = "关键词")
    private String keyword;

}
