package com.yibei.cms.domain.vo;

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
 * 内容分类视图对象 cms_category
 *
 * @author yibei
 * @date 2022-01-08
 */
@Data
@ApiModel("内容分类视图对象")
@ExcelIgnoreUnannotated
public class CmsCategoryVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  id
     */
	@ExcelProperty(value = "id")
	@ApiModelProperty("id")
	private Long id;

    /**
     * 父级id，可无限
     */
	@ExcelProperty(value = "父级id，可无限")
	@ApiModelProperty("父级id，可无限")
	private Long parentId;

    /**
     * 名称
     */
	@ExcelProperty(value = "名称")
	@ApiModelProperty("名称")
	private String name;

    /**
     * 类名
     */
	@ExcelProperty(value = "类名")
	@ApiModelProperty("类名")
	private String className;

    /**
     * 分类代码
     */
	@ExcelProperty(value = "分类代码")
	@ApiModelProperty("分类代码")
	private String code;

    /**
     * 图片
     */
	@ExcelProperty(value = "图片")
	@ApiModelProperty("图片")
	private String imgUrl;

    /**
     * 图片集
     */
	@ExcelProperty(value = "图片集")
	@ApiModelProperty("图片集")
	private String imgsUrl;

    /**
     * 权重 值越大越靠前
     */
	@ExcelProperty(value = "权重 值越大越靠前")
	@ApiModelProperty("权重 值越大越靠前")
	private Long weight;

    /**
     * 自定义属性
     */
	@ExcelProperty(value = "自定义属性")
	@ApiModelProperty("自定义属性")
	private String customProperty;

    /**
     * 备用字段
     */
	@ExcelProperty(value = "备用字段")
	@ApiModelProperty("备用字段")
	private String bak;

    /**
     * 备用字段2
     */
	@ExcelProperty(value = "备用字段2")
	@ApiModelProperty("备用字段2")
	private String bak2;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "stop_status")
	@ApiModelProperty("状态")
	private Long status;

    /**
     * 是否开启新增列表数据
     */
    @ExcelProperty(value = "是否开启新增列表数据", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "display_status")
	@ApiModelProperty("是否开启新增列表数据")
	private Long createDataEnable;

    /**
     * 是否开启新增子分类
     */
    @ExcelProperty(value = "是否开启新增子分类", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "display_status")
	@ApiModelProperty("是否开启新增子分类")
	private Long createChildEnable;

    /**
     * 子分类模板id
     */
	@ExcelProperty(value = "子分类模板id")
	@ApiModelProperty("子分类模板id")
	private Long childTemplateId;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
