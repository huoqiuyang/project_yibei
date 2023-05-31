package com.yibei.yb.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.yibei.common.annotation.ExcelDictFormat;
import com.yibei.common.convert.ExcelDictConvert;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TreeAddressSolveAbnormalListVo {

    /**
     *  ID
     */
    @ExcelProperty(value = "ID")
    @ApiModelProperty("ID")
    private Long id;

    /**
     * 父级id
     */
    @ExcelProperty(value = "父级id")
    @ApiModelProperty("父级id")
    private Long parentId;

    /**
     * 类别
     */
    @ExcelProperty(value = "类别", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "address_category")
    @ApiModelProperty("类别")
    private String category;

    /**
     * 省/直辖市
     */
    @ExcelProperty(value = "省/直辖市")
    @ApiModelProperty("省/直辖市")
    private String city;

    /**
     * 地区
     */
    @ExcelProperty(value = "地区")
    @ApiModelProperty("地区")
    private String region;

    /**
     * 状态
     */
    @ExcelProperty(value = "状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "relieve_abnormal_status")
    @ApiModelProperty("状态")
    private Integer status;

    /**
     * 户型
     */
    @ExcelProperty(value = "户型")
    @ApiModelProperty("户型")
    private Integer addressType;

    /**
     * 普通价
     */
    @ExcelProperty(value = "普通价")
    @ApiModelProperty("普通价")
    private BigDecimal ordinaryPrice;

    /**
     * 过度价
     */
    @ExcelProperty(value = "过度价")
    @ApiModelProperty("过度价")
    private BigDecimal transitionPrice;

    /**
     * 本地价
     */
    @ExcelProperty(value = "本地价")
    @ApiModelProperty("本地价")
    private BigDecimal localPrice;

    /**
     * 解三年异常价
     */
    @ExcelProperty(value = "解三年异常价")
    @ApiModelProperty("解三年异常价")
    private BigDecimal seriousPrice;

    /**
     * 发票价
     */
    @ExcelProperty(value = "发票价")
    @ApiModelProperty("发票价")
    private BigDecimal invoicesPrice;

    /**
     * 税点价
     */
    @ExcelProperty(value = "税点价")
    @ApiModelProperty("税点价")
    private BigDecimal taxPointPrice;

    /**
     * 变更价
     */
    @ExcelProperty(value = "变更价")
    @ApiModelProperty("变更价")
    private BigDecimal changePrice;

    /**
     * 法人到场价
     */
    @ExcelProperty(value = "法人到场价")
    @ApiModelProperty("法人到场价")
    private BigDecimal presenceLegalPersonPrice;

    /**
     * 注册价
     */
    @ExcelProperty(value = "注册价")
    @ApiModelProperty("注册价")
    private BigDecimal registerPrice;

    /**
     * 申请税控价
     */
    @ExcelProperty(value = "申请税控价")
    @ApiModelProperty("申请税控价")
    private BigDecimal applyTaxControlPrice;

    /**
     * 配合检验价
     */
    @ExcelProperty(value = "配合检验价")
    @ApiModelProperty("配合检验价")
    private BigDecimal inspectionPrice;

    /**
     * 其他价
     */
    @ExcelProperty(value = "其他价")
    @ApiModelProperty("其他价")
    private BigDecimal otherPrice;

    /**
     * 自定义价
     */
    @ExcelProperty(value = "自定义价")
    @ApiModelProperty("自定义价")
    private BigDecimal customPrice;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    @ApiModelProperty("备注")
    private String remarks;

    @ApiModelProperty("解异常子集")
    private List<TreeAddressSolveAbnormalListVo> list;
}
