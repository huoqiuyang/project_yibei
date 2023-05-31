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
 * 教材学习配置视图对象 yb_study_config
 *
 * @author yibei
 * @date 2022-05-12
 */
@Data
@ApiModel("教材学习配置视图对象")
@ExcelIgnoreUnannotated
public class YbStudyConfigVo extends BaseVo {

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
     * 用户id
     */
	@ExcelProperty(value = "用户id")
	@ApiModelProperty("用户id")
	private Long userId;

    /**
     * 每天学习数量
     */
	@ExcelProperty(value = "每天学习数量")
	@ApiModelProperty("每天学习数量")
	private Long quantity;

	/**
	 * 是否开启语音播放
	 */
	@ExcelProperty(value = "是否开启语音播放", converter = ExcelDictConvert.class)
	@ExcelDictFormat(dictType = "is_status")
	@ApiModelProperty("是否开启语音播放")
	private Integer isVoice;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
