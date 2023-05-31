package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class YbQuestionBankListVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("展示图")
    private String imgUrl;

    @ApiModelProperty("描述")
    private String describe;


    @ApiModelProperty("是否已加入书架（0未加入书架1已加入书架）")
    private int isBookshelf;

    @ApiModelProperty("总题数")
    private long allNum;

    @ApiModelProperty("知道题数")
    private long knowNum;

    @ApiModelProperty("不知道题数")
    private long dontKnowNum;

    @ApiModelProperty("已做完百分比")
    private String completedPercentage;

}
