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
 * 会员兑换码视图对象 yb_vip_code
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@ApiModel("会员兑换码视图对象")
@ExcelIgnoreUnannotated
public class YbVipCodeVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 兑换码
     */
	@ExcelProperty(value = "兑换码")
	@ApiModelProperty("兑换码")
	private String code;

    /**
     * 会员天数
     */
	@ExcelProperty(value = "会员天数")
	@ApiModelProperty("会员天数")
	private Integer vipDay;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "stop_status")
	@ApiModelProperty("状态")
	private Integer status;

    /**
     * 创建时间
     */
	@ExcelProperty(value = "创建时间")
	@ApiModelProperty("创建时间")
	private Date createTime;


}
