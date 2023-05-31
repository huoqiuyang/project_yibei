package com.yibei.yb.domain.clientVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class YbCommentsInfoVo {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("是否点赞(0否1是)")
    private int isUpvote;

    @ApiModelProperty("点赞量")
    private Long upvoteNum;

    @ApiModelProperty("评论内容")
    private String comments;

    @ApiModelProperty("置顶状态(0非置顶1置顶)")
    private Integer isTopping;

    @ApiModelProperty("评论回复量")
    private Long replyNum;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("评论回复列表")
    private List<YbCommentsReplyInfoVo> replyList;

}
