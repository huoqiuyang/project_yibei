package com.yibei.client.controller.yb;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.BeanCopyUtils;
import com.yibei.common.utils.PageUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.system.domain.User;
import com.yibei.system.service.IUserService;
import com.yibei.yb.domain.YbComments;
import com.yibei.yb.domain.YbUpvoteLog;
import com.yibei.yb.domain.clientBo.YbCommentsAddBo;
import com.yibei.yb.domain.clientBo.YbCommentsInfoBo;
import com.yibei.yb.domain.clientBo.YbUpvoteLogInfoBo;
import com.yibei.yb.domain.clientVo.YbCommentsInfoVo;
import com.yibei.yb.domain.clientVo.YbCommentsReplyInfoVo;
import com.yibei.yb.domain.clientVo.YbVipCommodityInfoVo;
import com.yibei.yb.service.IYbCommentsService;
import com.yibei.yb.service.IYbUpvoteLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController("Client_CommentsController")
@Api(value = "评论模块",tags = "评论模块")
@RequestMapping("/client/comments")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommentsController extends BaseController {

    private final IYbCommentsService iYbCommentsService;

    private final IYbUpvoteLogService iYbUpvoteLogService;

    private final IUserService iUserService;

    @ApiOperation("查询评论列表")
    @PostMapping("/list")
    @LoginChecked
    public TableDataInfo<YbCommentsInfoVo> list(@RequestBody YbCommentsInfoBo bo) {

        LambdaQueryWrapper<YbComments> lqw = new LambdaQueryWrapper<>();
        if(bo.getParentId()!=null && !bo.getParentId().equals(0l)){
            lqw.eq(YbComments::getParentId,bo.getParentId());
        }else{
            lqw.eq(YbComments::getContentId,bo.getContentId());
            lqw.eq(YbComments::getSourceType,bo.getSourceType());
            lqw.eq(YbComments::getParentId,0);
            lqw.orderByDesc(YbComments::getIsTopping);
        }
        lqw.orderByDesc(YbComments::getCreateTime);

        Page<YbComments> page = iYbCommentsService.page(new Page<>(bo.getPageNum(), bo.getPageSize()), lqw);
        List<YbCommentsInfoVo> listVo = new ArrayList<>();
        page.getRecords().forEach(item->{
            YbCommentsInfoVo vo = BeanCopyUtils.oneCopy(item, new CopyOptions(), YbCommentsInfoVo.class);
            //用户信息
            User user = iUserService.getById(item.getUserId());//发表评论的用户信息
            if(user!=null){
                vo.setNickName(user.getNickName());
                vo.setAvatar(user.getAvatar());
            }
            //点赞次数
            vo.setUpvoteNum(iYbUpvoteLogService.count(new LambdaQueryWrapper<YbUpvoteLog>().eq(YbUpvoteLog::getCommentsId, item.getId())));
            //用户是否点赞
            long num = iYbUpvoteLogService.count(new LambdaQueryWrapper<YbUpvoteLog>().eq(YbUpvoteLog::getCommentsId, item.getId()).eq(YbUpvoteLog::getUserId,getUserId()));
            if(num>0){
                vo.setIsUpvote(1);
            }else{
                vo.setIsUpvote(0);
            }
            //评论回复量
            vo.setReplyNum(iYbCommentsService.count(new LambdaQueryWrapper<YbComments>().eq(YbComments::getParentId, item.getId())));

            //评论回复列表
            LambdaQueryWrapper<YbComments> lqwR = new LambdaQueryWrapper<>();
            lqwR.eq(YbComments::getParentId,item.getId());
            lqwR.orderByDesc(YbComments::getCreateTime);
            List<YbCommentsReplyInfoVo> replyList = BeanCopyUtils.listCopy(iYbCommentsService.list(lqwR),new CopyOptions(), YbCommentsReplyInfoVo.class);
            replyList.forEach(reply->{
                //用户信息
                User userR = iUserService.getById(reply.getUserId());//发表评论的用户信息
                if(userR!=null){
                    reply.setNickName(userR.getNickName());
                    reply.setAvatar(userR.getAvatar());
                }
                //点赞次数
                reply.setUpvoteNum(iYbUpvoteLogService.count(new LambdaQueryWrapper<YbUpvoteLog>().eq(YbUpvoteLog::getCommentsId, reply.getId())));
                //用户是否点赞
                long numR = iYbUpvoteLogService.count(new LambdaQueryWrapper<YbUpvoteLog>().eq(YbUpvoteLog::getCommentsId, reply.getId()).eq(YbUpvoteLog::getUserId,getUserId()));
                if(numR>0){
                    reply.setIsUpvote(1);
                }else{
                    reply.setIsUpvote(0);
                }
            });
            vo.setReplyList(replyList);

            listVo.add(vo);
        });
        return PageUtils.buildDataInfo(listVo,page.getTotal());
    }

    @ApiOperation("发表评论")
    @PostMapping("/comment")
    @LoginChecked
    public AjaxResult commentBlog(@RequestBody YbCommentsAddBo bo){

        if(bo.getParentId()!=null && !bo.getParentId().equals(0l)){
            YbComments ybComments = iYbCommentsService.getById(bo.getParentId());
            if(ybComments == null){
                return AjaxResult.error("上级评论不存在");
            }else{
                bo.setContentId(ybComments.getContentId());
                bo.setSourceType(ybComments.getSourceType());
            }
        }

        YbComments comments = BeanCopyUtils.oneCopy(bo, new CopyOptions(), YbComments.class);
        comments.setUserId(getUserId());
        if(!iYbCommentsService.save(comments)){
            return AjaxResult.error("评论失败");
        }

        return AjaxResult.success("评论成功");
    }

    @ApiOperation("点赞")
    @PostMapping("/upvote")
    @LoginChecked
    public AjaxResult upvote(@RequestBody YbUpvoteLogInfoBo bo){

        YbComments ybComments = iYbCommentsService.getById(bo.getCommentsId());
        if(ybComments == null){
            return AjaxResult.error("评论不存在");
        }

        YbUpvoteLog ybUpvoteLog = iYbUpvoteLogService.getOne(new LambdaQueryWrapper<YbUpvoteLog>().eq(YbUpvoteLog::getCommentsId,bo.getCommentsId()).eq(YbUpvoteLog::getUserId,getUserId()).last("LIMIT 1"));

        Integer operationType = bo.getOperationType();//操作类型(1点赞2取消点赞)
        switch (operationType){
            case 1:
                //点赞
                if(ybUpvoteLog == null){
                    YbUpvoteLog upvoteLog = new YbUpvoteLog();
                    upvoteLog.setCommentsId(bo.getCommentsId());
                    upvoteLog.setUserId(getUserId());
                    if(!iYbUpvoteLogService.save(upvoteLog)){
                        return AjaxResult.error("点赞失败");
                    }
                    return AjaxResult.success("点赞成功");
                }else{
                    return AjaxResult.success("已点赞");
                }
            case 0:
                //取消点赞
                if(ybUpvoteLog!=null){
                    if(!iYbUpvoteLogService.removeById(ybUpvoteLog.getId())){
                        return AjaxResult.error("取消点赞失败");
                    }
                    return AjaxResult.success("取消点赞成功");
                }else{
                    return AjaxResult.success("该评论还未点赞");
                }
            default:
                return AjaxResult.error("操作类型错误");
        }
    }

}
