package com.yibei.system.domain.vo;

import com.yibei.system.vo.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
* 【请填写功能名称】视图对象 mall_package
*
* @author frame
* @date 2021-07-14
*/
@Data
public class AMapCityInfoVo {
    //省份名称
    private String province;
    //省份id
    private Integer provinceId;
    //城市名称
    private String city;
    //城市id
    private Integer cityId;
    //区县名称
    private String district;
    //取消id
    private Integer districtId;
    //完整地址名称
    private String formatAddress;
}
