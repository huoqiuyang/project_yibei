package com.yibei.yb.domain.clientVo;

import com.yibei.yb.domain.vo.YbContentLinkVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class YbTeachingMaterialEntryReadInfoVo {

    @ApiModelProperty("ID")
    private Long id;

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

//    @ApiModelProperty("相关链接")
//    private String relatedLinks;

    @ApiModelProperty("关键词")
    private String keyWord;



    @ApiModelProperty("相关链接")
    private List<YbContentLinkVo> ybContentLinkVoList;

    @ApiModelProperty("笔记")
    private String note;

    @ApiModelProperty("是否加入背诵：0未加入1已加入")
    private int isRecite;

    @ApiModelProperty("上一个词条id")
    private Long lastId;

    @ApiModelProperty("上一个词条标题")
    private String lastTitle;

    @ApiModelProperty("下一个词条id")
    private Long nextId;

    @ApiModelProperty("下一个词条标题")
    private String nextTitle;

}
