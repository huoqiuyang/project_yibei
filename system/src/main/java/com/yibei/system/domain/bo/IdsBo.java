package com.yibei.system.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class IdsBo {

    @NotNull(message = "ids不能为空")
    private List<Long> ids;
}
