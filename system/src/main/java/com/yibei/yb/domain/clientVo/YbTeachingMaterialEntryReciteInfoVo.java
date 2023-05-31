package com.yibei.yb.domain.clientVo;

import com.yibei.yb.domain.vo.YbContentLinkVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class YbTeachingMaterialEntryReciteInfoVo {

    @ApiModelProperty("词条ID")
    private Long entryId;

    @ApiModelProperty("教材id")
    private Long teachingMaterialId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("图片")
    private String imgUrl;

    @ApiModelProperty("重要度")
    private Long importance;

    @ApiModelProperty("音频")
    private String audioFrequency;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("相关链接")
    private String relatedLinks;

    @ApiModelProperty("关键词")
    private String keyWord;


    @ApiModelProperty("相关链接")
    private List<YbContentLinkVo> ybContentLinkVoList;

    @ApiModelProperty("笔记")
    private String note;


    @ApiModelProperty("背诵状态：0未背诵1背诵状态为知道2背诵状态为不知道")
    private int reciteStatus;

    @ApiModelProperty("词条在当天最近一次提交结果的时间")
    private Date lastTime;

    @ApiModelProperty("当天是否已答（0当天已答1当天未答）")
    private int isToday;

}
