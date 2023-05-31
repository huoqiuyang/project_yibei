package com.yibei.cms.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.yibei.common.annotation.Excel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 文章对象 cms_article
 *
 * @author yibei
 * @date 2022-01-08
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cms_article")
public class CmsArticle implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * id
     */
    @Excel(name = "")
    @TableId(value = "ID")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 分类id
     */
    @Excel(name = "分类")
    @TableField(value = "`category_id`")
    private Long categoryId;

    /**
     * 类型路径
     */
    @Excel(name = "类型路径")
    @TableField(value = "`category_path`")
    private String categoryPath;

    /**
     * 标识代码
     */
    @Excel(name = "标识代码")
    @TableField(value = "`code`")
    private String code;

    /**
     * 标题
     */
    @Excel(name = "标题")
    @TableField(value = "`title`")
    private String title;

    /**
     * 副标题
     */
    @Excel(name = "副标题")
    @TableField(value = "`sub_title`")
    private String subTitle;

    /**
     * 备用标题
     */
    @Excel(name = "备用标题")
    @TableField(value = "`bak_title`")
    private String bakTitle;

    /**
     * 描述、摘要
     */
    @Excel(name = "描述")
    @TableField(value = "`description`")
    private String description;

    /**
     * 备用描述
     */
    @Excel(name = "备用描述")
    @TableField(value = "`bak_description`")
    private String bakDescription;

    /**
     * 外链接
     */
    @Excel(name = "外链接")
    @TableField(value = "`link_url`")
    private String linkUrl;

    /**
     * 备用外链接
     */
    @Excel(name = "备用外链接")
    @TableField(value = "`sub_link_url`")
    private String subLinkUrl;

    /**
     * 封面图
     */
    @Excel(name = "封面图")
    @TableField(value = "`img_url`")
    private String imgUrl;

    /**
     * 相册，用英文逗号隔开
     */
    @Excel(name = "图片集")
    @TableField(value = "`imgs_url`")
    private String imgsUrl;

    /**
     * 视频链接
     */
    @Excel(name = "视频链接")
    @TableField(value = "`video_url`")
    private String videoUrl;

    /**
     * 附件
     */
    @Excel(name = "附件")
    @TableField(value = "`attachment`")
    private String attachment;

    /**
     * 内容
     */
    @Excel(name = "内容")
    @TableField(value = "`content`")
    private String content;

    /**
     * 内容备用
     */
    @Excel(name = "内容备用")
    @TableField(value = "`content_bak`")
    private String contentBak;

    /**
     * seo标题
     */
    @Excel(name = "SEO标题")
    @TableField(value = "`meta_title`")
    private String metaTitle;

    /**
     * seo描述
     */
    @Excel(name = "SEO描述")
    @TableField(value = "`meta_description`")
    private String metaDescription;

    /**
     * seo关键字
     */
    @Excel(name = "SEO关键字")
    @TableField(value = "`meta_keywords`")
    private String metaKeywords;

    /**
     * seo标题 备用字段
     */
    @Excel(name = "SEO标题备用")
    @TableField(value = "`meta_title_bak`")
    private String metaTitleBak;

    /**
     * seo描述 备用字段
     */
    @Excel(name = "SEO描述备用")
    @TableField(value = "`meta_description_bak`")
    private String metaDescriptionBak;

    /**
     * seo关键字 备用字段
     */
    @Excel(name = "SEO关键字备用")
    @TableField(value = "`meta_keywords_bak`")
    private String metaKeywordsBak;

    /**
     * 作者
     */
    @Excel(name = "作者")
    @TableField(value = "`author`")
    private String author;

    /**
     * 文章来源
     */
    @Excel(name = "文章来源")
    @TableField(value = "`source`")
    private String source;

    /**
     * 发布日期
     */
    @Excel(name = "发布日期")
    @TableField(value = "publish_time",fill = FieldFill.INSERT)
    private Date publishTime;

    /**
     * 点击量
     */
    @Excel(name = "点击量")
    @TableField(value = "`hit_count`")
    private Long hitCount;

    /**
     * 排序 越小越靠前
     */
    @Excel(name = "排序")
    @TableField(value = "`sort`")
    private Long sort;

    /**
     * 权重 越大越靠前
     */
    @Excel(name = "权重")
    @TableField(value = "`weight`")
    private Long weight;

    /**
     * 置顶
     */
    @Excel(name = "置顶")
    @TableField(value = "`is_top`")
    private Long isTop;

    /**
     * 推荐
     */
    @Excel(name = "推荐")
    @TableField(value = "`is_recommend`")
    private Long isRecommend;

    /**
     * 状态
     */
    @Excel(name = "状态")
    @TableField(value = "`status`")
    private Long status;

    /**
     * 外键备用字段
     */
    @Excel(name = "外键备用字段")
    @TableField(value = "`fk_bak1`")
    private String fkBak1;

    /**
     * 外键备用字段
     */
    @Excel(name = "外键备用字段")
    @TableField(value = "`fk_bak2`")
    private String fkBak2;

    /**
     * 外键备用字段
     */
    @Excel(name = "外键备用字段")
    @TableField(value = "`fk_bak3`")
    private String fkBak3;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    @TableField(value = "`bak1`")
    private String bak1;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    @TableField(value = "`bak2`")
    private String bak2;

    /**
     * 备用字段
     */
    @Excel(name = "备用字段")
    @TableField(value = "`bak3`")
    private String bak3;

    /**
     * 是否删除
     */
    @Excel(name = "删除")
    @TableField(value = "`is_deleted`")
    private Long isDeleted;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;
}
