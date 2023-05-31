package com.yibei.cms.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yibei.common.annotation.ExcelDictFormat;
import com.yibei.common.convert.ExcelDictConvert;
import com.yibei.system.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * 文章视图对象 cms_article
 *
 * @author yibei
 * @date 2022-01-08
 */
@Data
@ApiModel("文章视图对象")
@ExcelIgnoreUnannotated
public class CmsArticleVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  id
     */
	@ExcelProperty(value = "id")
	@ApiModelProperty("id")
	private Long id;

    /**
     * 分类id
     */
	@ExcelProperty(value = "分类id")
	@ApiModelProperty("分类id")
	private Long categoryId;

    /**
     * 类型路径
     */
	@ExcelProperty(value = "类型路径")
	@ApiModelProperty("类型路径")
	private String categoryPath;

    /**
     * 标识代码
     */
	@ExcelProperty(value = "标识代码")
	@ApiModelProperty("标识代码")
	private String code;

    /**
     * 标题
     */
	@ExcelProperty(value = "标题")
	@ApiModelProperty("标题")
	private String title;

    /**
     * 副标题
     */
	@ExcelProperty(value = "副标题")
	@ApiModelProperty("副标题")
	private String subTitle;

    /**
     * 备用标题
     */
	@ExcelProperty(value = "备用标题")
	@ApiModelProperty("备用标题")
	private String bakTitle;

    /**
     * 描述、摘要
     */
	@ExcelProperty(value = "描述、摘要")
	@ApiModelProperty("描述、摘要")
	private String description;

    /**
     * 备用描述
     */
	@ExcelProperty(value = "备用描述")
	@ApiModelProperty("备用描述")
	private String bakDescription;

    /**
     * 外链接
     */
	@ExcelProperty(value = "外链接")
	@ApiModelProperty("外链接")
	private String linkUrl;

    /**
     * 备用外链接
     */
	@ExcelProperty(value = "备用外链接")
	@ApiModelProperty("备用外链接")
	private String subLinkUrl;

    /**
     * 封面图
     */
	@ExcelProperty(value = "封面图")
	@ApiModelProperty("封面图")
	private String imgUrl;

    /**
     * 相册，用英文逗号隔开
     */
	@ExcelProperty(value = "相册，用英文逗号隔开")
	@ApiModelProperty("相册，用英文逗号隔开")
	private String imgsUrl;

    /**
     * 视频链接
     */
	@ExcelProperty(value = "视频链接")
	@ApiModelProperty("视频链接")
	private String videoUrl;

    /**
     * 附件
     */
	@ExcelProperty(value = "附件")
	@ApiModelProperty("附件")
	private String attachment;

    /**
     * 内容
     */
	@ExcelProperty(value = "内容")
	@ApiModelProperty("内容")
	private String content;

    /**
     * 内容备用
     */
	@ExcelProperty(value = "内容备用")
	@ApiModelProperty("内容备用")
	private String contentBak;

    /**
     * seo标题
     */
	@ExcelProperty(value = "seo标题")
	@ApiModelProperty("seo标题")
	private String metaTitle;

    /**
     * seo描述
     */
	@ExcelProperty(value = "seo描述")
	@ApiModelProperty("seo描述")
	private String metaDescription;

    /**
     * seo关键字
     */
	@ExcelProperty(value = "seo关键字")
	@ApiModelProperty("seo关键字")
	private String metaKeywords;

    /**
     * seo标题 备用字段
     */
	@ExcelProperty(value = "seo标题 备用字段")
	@ApiModelProperty("seo标题 备用字段")
	private String metaTitleBak;

    /**
     * seo描述 备用字段
     */
	@ExcelProperty(value = "seo描述 备用字段")
	@ApiModelProperty("seo描述 备用字段")
	private String metaDescriptionBak;

    /**
     * seo关键字 备用字段
     */
	@ExcelProperty(value = "seo关键字 备用字段")
	@ApiModelProperty("seo关键字 备用字段")
	private String metaKeywordsBak;

    /**
     * 作者
     */
	@ExcelProperty(value = "作者")
	@ApiModelProperty("作者")
	private String author;

    /**
     * 文章来源
     */
	@ExcelProperty(value = "文章来源")
	@ApiModelProperty("文章来源")
	private String source;

    /**
     * 发布日期
     */
	@ExcelProperty(value = "发布日期")
	@ApiModelProperty("发布日期")
	private Date publishTime;

    /**
     * 点击量
     */
	@ExcelProperty(value = "点击量")
	@ApiModelProperty("点击量")
	private Long hitCount;

    /**
     * 排序 越小越靠前
     */
	@ExcelProperty(value = "排序 越小越靠前")
	@ApiModelProperty("排序 越小越靠前")
	private Long sort;

    /**
     * 权重 越大越靠前
     */
	@ExcelProperty(value = "权重 越大越靠前")
	@ApiModelProperty("权重 越大越靠前")
	private Long weight;

    /**
     * 置顶
     */
    @ExcelProperty(value = "置顶", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "display_status")
	@ApiModelProperty("置顶")
	private Long isTop;

    /**
     * 推荐
     */
    @ExcelProperty(value = "推荐", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "is_status")
	@ApiModelProperty("推荐")
	private Long isRecommend;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "is_status")
	@ApiModelProperty("状态")
	private Long status;

    /**
     * 外键备用字段
     */
	@ExcelProperty(value = "外键备用字段")
	@ApiModelProperty("外键备用字段")
	private String fkBak1;

    /**
     * 外键备用字段
     */
	@ExcelProperty(value = "外键备用字段")
	@ApiModelProperty("外键备用字段")
	private String fkBak2;

    /**
     * 外键备用字段
     */
	@ExcelProperty(value = "外键备用字段")
	@ApiModelProperty("外键备用字段")
	private String fkBak3;

    /**
     * 备用字段
     */
	@ExcelProperty(value = "备用字段")
	@ApiModelProperty("备用字段")
	private String bak1;

    /**
     * 备用字段
     */
	@ExcelProperty(value = "备用字段")
	@ApiModelProperty("备用字段")
	private String bak2;

    /**
     * 备用字段
     */
	@ExcelProperty(value = "备用字段")
	@ApiModelProperty("备用字段")
	private String bak3;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
