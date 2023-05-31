package com.yibei.yb.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.yibei.common.annotation.ExcelDictFormat;
import com.yibei.common.convert.ExcelDictConvert;
import com.yibei.system.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 服务分类视图对象 yzyw_service_class
 *
 * @author yibei
 * @date 2021-12-15
 */
@Data
@ApiModel("服务分类视图对象")
public class ServiceClassTreeVo {

	/**
     *  id
     */
	@ExcelProperty(value = "id")
	@ApiModelProperty("id")
	private Long id;

    /**
     * 父级id
     */
	@ExcelProperty(value = "父级id")
	@ApiModelProperty("父级id")
	private Long parentId;

    /**
     * 名称
     */
	@ExcelProperty(value = "名称")
	@ApiModelProperty("名称")
	private String name;

    /**
     * 图标
     */
	@ExcelProperty(value = "图标")
	@ApiModelProperty("图标")
	private String icon;

    /**
     * 链接
     */
	@ExcelProperty(value = "链接")
	@ApiModelProperty("链接")
	private String link;

    /**
     * 小程序路径
     */
	@ExcelProperty(value = "小程序路径")
	@ApiModelProperty("小程序路径")
	private String pages;

	@ApiModelProperty("分类子列表")
	private List<ServiceClassTreeVo> list;
}
