package com.yibei.yb.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CompanyDicBo {

    @ApiModelProperty("类型：1行业类别，2附带资产，3企业类型，4纳税类型，5公司地区，6是否开户")
    private Integer type;

}
