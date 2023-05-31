package com.yibei.yb.domain.vo;

import java.math.BigDecimal;
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
 * 会员中心视图对象 yb_vip_commodity
 *
 * @author yibei
 * @date 2022-04-27
 */
@Data
@ApiModel("会员中心视图对象")
@ExcelIgnoreUnannotated
public class YbVipCommodityVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	/**
     *  ID
     */
	@ExcelProperty(value = "ID")
	@ApiModelProperty("ID")
	private Long id;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "vip_type")
	@ApiModelProperty("标题")
	private String title;

    /**
     * 标签
     */
	@ExcelProperty(value = "标签")
	@ApiModelProperty("标签")
	private String label;

    /**
     * 月数
     */
	@ExcelProperty(value = "月数", converter = ExcelDictConvert.class)
	@ExcelDictFormat(dictType = "month")
	@ApiModelProperty("月数")
	private Integer month;

    /**
     * 价格
     */
	@ExcelProperty(value = "价格")
	@ApiModelProperty("价格")
	private BigDecimal price;

    /**
     * 单月价
     */
	@ExcelProperty(value = "单月价")
	@ApiModelProperty("单月价")
	private BigDecimal precioMensual;

    /**
     * 排序
     */
	@ExcelProperty(value = "排序")
	@ApiModelProperty("排序")
	private Long sort;

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
