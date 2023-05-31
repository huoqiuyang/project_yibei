package com.yibei.system.domain.clientBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IdBo {
    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;
}
