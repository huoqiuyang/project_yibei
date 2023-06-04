package com.yibei.yb.domain.clientBo;

import com.yibei.yb.domain.clientVo.YbTmeCatalogueSecondInfoVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PlayListBO {

    @NotNull(message = "materialId不能为空")
    @ApiModelProperty(value = "教材id")
    private Long materialId;

    @ApiModelProperty(value = "词条列表，注意顺序")
    private List<YbTmeCatalogueSecondInfoVo> entryList;

}
