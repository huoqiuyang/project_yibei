package com.yibei.system.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ParentIdBo {

    @ApiModelProperty(value = "父级id，顶级id传0")
	@NotNull(message = "父级id不能为空")
    private Integer parentId=0;
}
