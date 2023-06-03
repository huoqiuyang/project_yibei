package com.yibei.yb.domain.vo;

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
 * 教材词条视图对象 yb_teaching_material_entry
 *
 * @author yibei
 * @date 2022-05-14
 */
@Data
@ApiModel("教材词条视图对象")
@ExcelIgnoreUnannotated
public class YbTeachingMaterialEntryVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
	 *  ID
	 */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

	/**
	 * 教材id
	 */
	@ExcelProperty(value = "教材id")
	@ApiModelProperty("教材id")
	private Long teachingMaterialId;

	/**
	 * 上级id
	 */
	@ExcelProperty(value = "上级id")
	@ApiModelProperty("上级id")
	private Long parentId;

	/**
	 * 标题
	 */
	@ExcelProperty(value = "标题")
	@ApiModelProperty("标题")
	private String title;

	/**
	 * 标签
	 */
	@ExcelProperty(value = "标签", converter = ExcelDictConvert.class)
	@ExcelDictFormat(dictType = "label_type")
	@ApiModelProperty("标签")
	private String label;

	/**
	 * 图片
	 */
	@ExcelProperty(value = "图片")
	@ApiModelProperty("图片")
	private String imgUrl;

	/**
	 * 重要度
	 */
	@ExcelProperty(value = "重要度", converter = ExcelDictConvert.class)
	@ExcelDictFormat(dictType = "importance_type")
	@ApiModelProperty("重要度")
	private Long importance;

	/**
	 * 音频
	 */
	@ExcelProperty(value = "音频")
	@ApiModelProperty("音频")
	private String audioFrequency;

	/**
	 * 相关链接
	 */
	@ExcelProperty(value = "相关链接")
	@ApiModelProperty("相关链接")
	private String relatedLinks;

	/**
	 * 拓展信息
	 */
	@ExcelProperty(value = "拓展信息")
	@ApiModelProperty(value = "拓展信息")
	private String extendingInfo;

	/**
	 * 答题点播
	 */
	@ExcelProperty(value = "答题点播")
	@ApiModelProperty(value = "答题点播")
	private String answerTip;

	/**
	 * 内容
	 */
	@ExcelProperty(value = "内容")
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 排序
	 */
	@ExcelProperty(value = "排序")
	@ApiModelProperty("排序")
	private Long sort;

	/**
	 * 关键词
	 */
	@ExcelProperty(value = "关键词")
	@ApiModelProperty("关键词")
	private String keyWord;

	/**
	 * 状态
	 */
	@ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
	@ExcelDictFormat(dictType = "display_status")
	@ApiModelProperty("状态")
	private Integer status;

	/**
	 * 创建时间
	 */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}