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
 * 相关链接视图对象 v_link
 *
 * @author yibei
 * @date 2022-06-09
 */
@Data
@ApiModel("相关链接视图对象")
@ExcelIgnoreUnannotated
public class VLinkVo extends BaseVo {

	private static final long serialVersionUID = 1L;

//	/**
//     *  类型
//     */
//	@ExcelProperty(value = "类型")
//	@ApiModelProperty("类型")
//	private String type;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "source_type")
	@ApiModelProperty("类型")
	private String type;

    /**
     * 词条ID
     */
	@ExcelProperty(value = "词条ID")
	@ApiModelProperty("词条ID")
	private Long id;

    /**
     * 书籍ID
     */
	@ExcelProperty(value = "书籍ID")
	@ApiModelProperty("书籍ID")
	private Long bookId;

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
	private String importance;

    /**
     * 排序
     */
	@ExcelProperty(value = "排序")
	@ApiModelProperty("排序")
	private Long sort;

    /**
     * 书籍名称
     */
	@ExcelProperty(value = "书籍名称")
	@ApiModelProperty("书籍名称")
	private String bookTitle;


}
