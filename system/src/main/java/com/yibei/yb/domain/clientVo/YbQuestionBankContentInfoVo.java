package com.yibei.yb.domain.clientVo;

import com.yibei.yb.domain.vo.YbContentLinkVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class YbQuestionBankContentInfoVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("题库id")
    private Long questionBankId;

    @ApiModelProperty("题库名称")
    private String questionBankName;

//    @ApiModelProperty("上级id")
//    private Long parentId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("答案")
    private String answer;

    @ApiModelProperty("图片")
    private String imgUrl;

    @ApiModelProperty("标签")
    private String label;

    @ApiModelProperty("重要度")
    private Integer importance;

//    @ApiModelProperty("相关链接")
//    private String relatedLinks;

    @ApiModelProperty("当前题目是否收藏（1已收藏0未收藏）")
    private int isCollection;


    @ApiModelProperty("相关链接")
    private List<YbContentLinkVo> ybContentLinkVoList;

    @ApiModelProperty("上一个词条id")
    private Long lastId;

    @ApiModelProperty("上一个词条标题")
    private String lastTitle;

    @ApiModelProperty("下一个词条id")
    private Long nextId;

    @ApiModelProperty("下一个词条标题")
    private String nextTitle;


    @ApiModelProperty("题库词条数")
    private Integer totalNum;

    @ApiModelProperty("题库答题数（用户回答知道）")
    private Integer userAnsweredNum;

}
