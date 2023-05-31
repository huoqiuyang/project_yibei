package com.yibei.system.domain.bo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class DictTypeListBo {
    @NotBlank
    private String dictTypes;
}
