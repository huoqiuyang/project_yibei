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
 * 评论视图对象 yb_comments
 *
 * @author yibei
 * @date 2022-05-19
 */
@Data
@ApiModel("评论视图对象")
@ExcelIgnoreUnannotated
public class YbCommentsVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 内容id
     */
	@ExcelProperty(value = "内容id")
	@ApiModelProperty("内容id")
	private Long contentId;

    /**
     * 上级id
     */
	@ExcelProperty(value = "上级id")
	@ApiModelProperty("上级id")
	private Long parentId;

    /**
     * 类型
     */
    @ExcelProperty(value = "类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "source_type")
	@ApiModelProperty("类型")
	private Long sourceType;

    /**
     * 评论内容
     */
	@ExcelProperty(value = "评论内容")
	@ApiModelProperty("评论内容")
	private String comments;

    /**
     * 置顶状态
     */
    @ExcelProperty(value = "置顶状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "topping_type")
	@ApiModelProperty("置顶状态")
	private Integer isTopping;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
