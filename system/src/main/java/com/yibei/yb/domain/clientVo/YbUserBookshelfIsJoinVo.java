package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbUserBookshelfIsJoinVo {

    @ApiModelProperty("是否加入书架（0未加入书架，1已加入书架）")
    private long isBookshelf;

}
