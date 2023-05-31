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
 * 用户收藏视图对象 yb_user_collection
 *
 * @author yibei
 * @date 2022-05-06
 */
@Data
@ApiModel("用户收藏视图对象")
@ExcelIgnoreUnannotated
public class YbUserCollectionVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 教材题库类型
     */
    @ExcelProperty(value = "教材题库类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "content_type")
	@ApiModelProperty("教材题库类型")
	private Long contentType;

    /**
     * 内容id
     */
	@ExcelProperty(value = "内容id")
	@ApiModelProperty("内容id")
	private Long contentId;

    /**
     * 词条id
     */
	@ExcelProperty(value = "词条id")
	@ApiModelProperty("词条id")
	private Long entryId;

    /**
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
