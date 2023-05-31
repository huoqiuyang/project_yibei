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
@ApiModel("【请填写功能名称】视图对象")
public class CityInfoVo extends BaseVo {
private static final long serialVersionUID = 1L;

	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("parentId")
	private Integer parentId;

	@ApiModelProperty("name")
	private String name;

    @ApiModelProperty("区域代码")
    private String zipcode;

    @ApiModelProperty("地区级别 1省 2市 3区")
    private Integer regionLevel;

    @ApiModelProperty("0未开通 1已开通")
    private Integer state;

    @ApiModelProperty("0未开通 1已开通")
    private Integer isDeleted;
}
