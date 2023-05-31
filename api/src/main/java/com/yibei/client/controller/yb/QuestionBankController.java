package com.yibei.client.controller.yb;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.BeanCopyUtils;
import com.yibei.common.utils.CalculationUtils;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.utils.TimeUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.system.domain.clientBo.IdBo;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.yb.domain.*;
import com.yibei.yb.domain.clientBo.YbAnswerLogInfoBo;
import com.yibei.yb.domain.clientBo.YbQuestionBankContentInfoBo;
import com.yibei.yb.domain.clientBo.YbUserCollectionAddBo;
import com.yibei.yb.domain.clientBo.YbUserCollectionInfoBo;
import com.yibei.yb.domain.clientVo.YbQuestionBankContentInfoVo;
import com.yibei.yb.domain.clientVo.YbQuestionBankListVo;
import com.yibei.yb.domain.clientVo.YbQbcCatalogueInfoVo;
import com.yibei.yb.domain.vo.YbContentLinkVo;
import com.yibei.yb.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController("Client_QuestionBankController")
@Api(value = "题库模块",tags = "题库模块")
@RequestMapping("/client/questionBank")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class QuestionBankController extends BaseController {

    private final IYbQuestionBankService iYbQuestionBankService;

    private final IYbQuestionBankContentService iYbQuestionBankContentService;

    private final IYbUserBookshelfService iYbUserBookshelfService;

    private final IYbUserCollectionService iYbUserCollectionService;

    private final IYbAnswerLogService iYbAnswerLogService;

    private final IYbContentLinkService iYbContentLinkService;

    @ApiOperation("题库列表")
    @PostMapping("/list")
    public TableDataInfo<YbQuestionBankListVo> list(@RequestBody PageBo bo) {

        LambdaQueryWrapper<YbQuestionBank> lqw = new LambdaQueryWrapper<>();
        lqw.eq(YbQuestionBank::getStatus,1);

        lqw.orderByAsc(YbQuestionBank::getSort);
        lqw.orderByDesc(YbQuestionBank::getCreateTime);

        Page<YbQuestionBank> page = iYbQuestionBankService.page(new Page<>(bo.getPageNum(), bo.getPageSize()), lqw);
        List<YbQuestionBankListVo> listVo = BeanCopyUtils.listCopy(page.getRecords(),new CopyOptions(),YbQuestionBankListVo.class);

        Long userId = getUserId();
        listVo.forEach(item->{
            //总题数
            long allNum = iYbQuestionBankContentService.count(new LambdaQueryWrapper<YbQuestionBankContent>()
                    .eq(YbQuestionBankContent::getStatus,1)
                    .gt(YbQuestionBankContent::getParentId,0)
                    .eq(YbQuestionBankContent::getQuestionBankId,item.getId()));
            item.setAllNum(allNum);

            if(userId!=null && !userId.equals(0l)){
                //是否加入书架
                YbUserBookshelf userBookshelf = iYbUserBookshelfService.getUserBookshelf(2,item.getId(),userId);
                if(userBookshelf!=null){
                    item.setIsBookshelf(1);
                }else{
                    item.setIsBookshelf(0);
                }

                //知道题数
//                long knowNum = iYbAnswerLogService.count(new LambdaQueryWrapper<YbAnswerLog>()
//                        .eq(YbAnswerLog::getQuestionBankId,item.getId())
//                        .eq(YbAnswerLog::getUserId,userId)
//                        .eq(YbAnswerLog::getType,1));
                long knowNum = iYbQuestionBankContentService.getAnswersNum(item.getId(),userId,1);
                item.setKnowNum(knowNum);
                //不知道题数
//                long dontKnowNum = iYbAnswerLogService.count(new LambdaQueryWrapper<YbAnswerLog>()
//                        .eq(YbAnswerLog::getQuestionBankId,item.getId())
//                        .eq(YbAnswerLog::getUserId,userId)
//                        .eq(YbAnswerLog::getType,2));
                long dontKnowNum = iYbQuestionBankContentService.getAnswersNum(item.getId(),userId,2);
                item.setDontKnowNum(dontKnowNum);
                //已做完百分比
                item.setCompletedPercentage(CalculationUtils.division(knowNum+dontKnowNum,allNum));
            }
        });
        return PageUtils.buildDataInfo(listVo,page.getTotal());
    }

    @ApiOperation("题库详情")
    @PostMapping("/questionBankDetail")
    public AjaxResult<YbQuestionBankListVo> questionBankDetail(@RequestBody IdBo bo) {

        YbQuestionBank questionBank = iYbQuestionBankService.getById(bo.getId());
        if(questionBank == null){
            return AjaxResult.error("题库不存在",null);
        }

        YbQuestionBankListVo vo = BeanCopyUtils.oneCopy(questionBank, new CopyOptions(), YbQuestionBankListVo.class);

        Long userId = getUserId();
        //总题数
        long allNum = iYbQuestionBankContentService.count(new LambdaQueryWrapper<YbQuestionBankContent>()
                .eq(YbQuestionBankContent::getStatus,1)
                .gt(YbQuestionBankContent::getParentId,0)
                .eq(YbQuestionBankContent::getQuestionBankId,vo.getId()));
        vo.setAllNum(allNum);
        if(userId!=null && !userId.equals(0l)){
            //是否加入书架
            YbUserBookshelf userBookshelf = iYbUserBookshelfService.getUserBookshelf(2,vo.getId(),userId);
            if(userBookshelf!=null){
                vo.setIsBookshelf(1);
            }else{
                vo.setIsBookshelf(0);
            }
            //知道题数
            long knowNum = iYbQuestionBankContentService.getAnswersNum(vo.getId(),userId,1);
            vo.setKnowNum(knowNum);
            //不知道题数
            long dontKnowNum = iYbQuestionBankContentService.getAnswersNum(vo.getId(),userId,2);
            vo.setDontKnowNum(dontKnowNum);
            //已做完百分比
            vo.setCompletedPercentage(CalculationUtils.division(knowNum+dontKnowNum,allNum));
        }

        return AjaxResult.success(vo);
    }

    @ApiOperation("题目详情")
    @PostMapping("/detail")
    @LoginChecked
    public AjaxResult<YbQuestionBankContentInfoVo> detail(@RequestBody YbQuestionBankContentInfoBo bo) {

        //验证会员，非会员用户不能进入详情页面
        YbUserVip ybUserVip = iUserService.getUserVipInfo(getUserId());
        if(ybUserVip==null){
            return AjaxResult.error("还不是会员，暂时无法查看",null);
        }

        //获取题目详情信息
        Long selectId = null;//查询题目ID
        if(bo.getId()==null){
            //跳转此题库上一次打开的题目，从没打开过的跳转第一个题目
            YbAnswerLog answerLog = iYbAnswerLogService.getOne(new LambdaQueryWrapper<YbAnswerLog>()
                    .eq(YbAnswerLog::getQuestionBankId,bo.getQuestionBankId())
                    .eq(YbAnswerLog::getUserId,getUserId())
                    .orderByDesc(YbAnswerLog::getLastTime)
                    .last("LIMIT 1"));
            if(answerLog!=null){
                bo.setId(answerLog.getQuestionBankContentId());
            }
        }
        if(bo.getId()!=null){
            //题目id不为空，查询此题目信息
            YbQuestionBankContent questionBankContent = iYbQuestionBankContentService.getById(bo.getId());
            if(questionBankContent!=null){
                selectId = questionBankContent.getId();
            }
        }
        /*if(bo.getId()!=null){
            //题目id不为空，查询此题目信息
            YbQuestionBankContent questionBankContent = iYbQuestionBankContentService.getById(bo.getId());
            if(questionBankContent!=null){
                selectId = questionBankContent.getId();
            }
        }*/
        YbQuestionBankContentInfoVo vo = iYbQuestionBankContentService.getSubjectInfo(bo.getQuestionBankId(),selectId);

        //题库名称
        YbQuestionBank questionBank = iYbQuestionBankService.getById(vo.getQuestionBankId());
        if(questionBank!=null){
            vo.setQuestionBankName(questionBank.getTitle());
        }

        //用户是否收藏
        YbUserCollection ybUserCollection = iYbUserCollectionService.getOne(new LambdaQueryWrapper<YbUserCollection>()
                .eq(YbUserCollection::getUserId,getUserId())
                .eq(YbUserCollection::getContentType,2l)
                .eq(YbUserCollection::getEntryId,vo.getId())
                .last("LIMIT 1"));
        vo.setIsCollection(ybUserCollection!=null?1:0);

        //查询词条的相关链接信息
        List<YbContentLinkVo> ybContentLinkVoListst = iYbContentLinkService.listVo(new LambdaQueryWrapper<YbContentLink>()
                .eq(YbContentLink::getSourceType,2l)
                .eq(YbContentLink::getEntryId,vo.getId())
                .orderByDesc(YbContentLink::getCreateTime));
        vo.setYbContentLinkVoList(iYbContentLinkService.queryExtData(ybContentLinkVoListst));

        //该题库题目总数、用户在该题库回答知道的题目数量
        Map<String, BigDecimal> map = iYbQuestionBankContentService.getAnswerInfo(vo.getQuestionBankId(),getUserId());
        vo.setTotalNum(map.get("total").intValue());
        vo.setUserAnsweredNum(map.get("answered").intValue());

        //刷新最近打开时间
        iYbUserBookshelfService.updateLastTime(getUserId(),2l,bo.getQuestionBankId());

        return AjaxResult.success(vo);
    }

    /*@ApiOperation("题目详情")
    @PostMapping("/detail")
    @LoginChecked
    public AjaxResult detail1(@RequestBody YbQuestionBankContentInfoBo bo) {

        Map<String, Object> result = new HashMap<>(2);

        //验证会员，非会员用户不能进入详情页面
        YbUserVip ybUserVip = iUserService.getUserVipInfo(getUserId());
        if(ybUserVip == null){
            result.put("info", null);
            result.put("isLast", 0);
            result.put("userVip", 0);
            return AjaxResult.error("还不是会员，暂时无法查看",result);
        }else{
            result.put("isLast", 0);
            result.put("userVip", 1);
        }

        //获取题目详情信息
        YbQuestionBankContent questionBankContent = null;
        if(bo.getId()!=null && !bo.getId().equals(0l)){
            //题目id不为空，查询此题目信息
            questionBankContent = iYbQuestionBankContentService.getById(bo.getId());
        }else{
            List<YbQuestionBankContent> sortList = iYbQuestionBankContentService.getListBysort(bo.getQuestionBankId());
            if(sortList!=null && sortList.size()>0){
                if(bo.getCurrentId()!=null){
                    //题目id为空，当前题目id不为空,查询下一道题目的信息
                    int subscript = -1;
                    for(int i=0;i<sortList.size();i++){
                        YbQuestionBankContent qbc = sortList.get(i);
                        if(qbc.getId().equals(bo.getCurrentId())){
                            subscript = i;
                            break;
                        }
                    }
                    //最后一题特殊处理
                    if(subscript+1==sortList.size()){
                        result.put("isLast", 1);
                        subscript = -1;
                    }
                    questionBankContent = sortList.get(subscript+1);
                }else{
                    //题目id为空，当前题目id为空,查询此题库里的第一道题目的信息
                    questionBankContent = sortList.get(0);
                }
            }
        }
        result.put("info", BeanCopyUtils.oneCopy(questionBankContent, new CopyOptions(), YbQuestionBankContentInfoVo.class));

        //刷新最近打开时间
        iYbUserBookshelfService.updateLastTime(getUserId(),2l,bo.getQuestionBankId());

        return AjaxResult.success(result);
    }*/

    @ApiOperation("加入收藏")
    @PostMapping("/addCollection")
    @LoginChecked
    public AjaxResult addCollection(@RequestBody YbUserCollectionAddBo bo) {

        YbQuestionBankContent questionBankContent = iYbQuestionBankContentService.getById(bo.getId());
        if(questionBankContent == null){
            return AjaxResult.error("题目不存在");
        }

        YbUserCollection ybUserCollection = iYbUserCollectionService.getOne(new LambdaQueryWrapper<YbUserCollection>()
                .eq(YbUserCollection::getUserId,getUserId())
                .eq(YbUserCollection::getContentType,2)
                .eq(YbUserCollection::getEntryId,questionBankContent.getId())
                .last("LIMIT 1"));

        int operationType = bo.getOperationType();//操作类型 1加入收藏2,移出收藏
        switch (operationType){
            case 1:
                //加入收藏
                if(ybUserCollection!=null){
                    return AjaxResult.error("请勿重复收藏");
                }

                YbUserCollection userCollection = new YbUserCollection();
                userCollection.setUserId(getUserId());
                userCollection.setEntryId(questionBankContent.getId());
                userCollection.setContentId(questionBankContent.getQuestionBankId());
                userCollection.setContentType(2l);

                if(!iYbUserCollectionService.save(userCollection)){
                    return AjaxResult.error("收藏失败");
                }
                return AjaxResult.success("收藏成功");
            case 2:
                //移出收藏
                if(ybUserCollection == null){
                    return AjaxResult.error("还未收藏");
                }

                UpdateWrapper uw = new UpdateWrapper();
                uw.eq("id",ybUserCollection.getId());
                uw.setSql("is_deleted=1");

                if(!iYbUserCollectionService.update(uw)){
                    return AjaxResult.error("取消收藏失败");
                }
                return AjaxResult.success("取消收藏成功");
            default:
                return AjaxResult.error("操作错误");
        }
    }

    @ApiOperation("目录列表")
    @PostMapping("/catalogueList")
    public AjaxResult catalogueList(@RequestBody YbUserCollectionInfoBo bo) {

        Long userId = getUserId();

        List<YbQuestionBankContent> list = iYbQuestionBankContentService.list(new LambdaQueryWrapper<YbQuestionBankContent>()
                .eq(YbQuestionBankContent::getQuestionBankId,bo.getId())
                .eq(YbQuestionBankContent::getStatus,1)
                .eq(YbQuestionBankContent::getParentId,0)
                .orderByAsc(YbQuestionBankContent::getSort).orderByDesc(YbQuestionBankContent::getCreateTime));
        List<YbQbcCatalogueInfoVo> listVo = BeanCopyUtils.listCopy(list,new CopyOptions(), YbQbcCatalogueInfoVo.class);

        listVo.forEach(item->{
            item.setSecondList(iYbQuestionBankContentService.getSecondList(userId==null?0l:userId,item.getId(),0));
        });

        return AjaxResult.success(listVo);
    }

    @ApiOperation("收藏列表")
    @PostMapping("/collectionList")
    @LoginChecked
    public AjaxResult collectionList(@RequestBody YbUserCollectionInfoBo bo) {

        List<YbQbcCatalogueInfoVo> listVo = iYbQuestionBankContentService.getCollectionList(getUserId(),bo.getId());

        listVo.forEach(item->{
            item.setSecondList(iYbQuestionBankContentService.getSecondList(getUserId(),item.getId(),1));
        });

        return AjaxResult.success(listVo);
    }

    @ApiOperation("做题提交")
    @PostMapping("/makeQuestion")
    @LoginChecked
    public AjaxResult makeQuestion(@RequestBody YbAnswerLogInfoBo bo) {

        YbQuestionBankContent questionBankContent = iYbQuestionBankContentService.getById(bo.getId());
        if(questionBankContent == null){
            return AjaxResult.error("题目不存在");
        }

        YbAnswerLog answerLog = iYbAnswerLogService.getOne(new LambdaQueryWrapper<YbAnswerLog>()
                .eq(YbAnswerLog::getQuestionBankContentId,bo.getId())
                .eq(YbAnswerLog::getUserId,getUserId())
                .last("LIMIT 1"));

        if(answerLog!=null){
            //更改答题记录
            answerLog.setType(bo.getType());
            answerLog.setLastTime(TimeUtils.getCurrentDate());
            if(!iYbAnswerLogService.updateById(answerLog)){
                return AjaxResult.error("做题提交失败");
            }
        }else{
            //新建答题记录
            answerLog = new YbAnswerLog();
            answerLog.setQuestionBankId(questionBankContent.getQuestionBankId());
            answerLog.setQuestionBankContentId(bo.getId());
            answerLog.setType(bo.getType());
            answerLog.setUserId(getUserId());
            answerLog.setLastTime(TimeUtils.getCurrentDate());
            if(!iYbAnswerLogService.save(answerLog)){
                return AjaxResult.error("做题提交失败");
            }
        }

        return AjaxResult.success("做题提交成功");
    }

}
