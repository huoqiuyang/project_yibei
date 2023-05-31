package com.yibei.client.controller.yb;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.CalculationUtils;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.utils.TimeUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.system.domain.SysConfig;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.system.service.ISysConfigService;
import com.yibei.yb.domain.*;
import com.yibei.yb.domain.clientBo.YbUserBookshelfAddBo;
import com.yibei.yb.domain.clientBo.YbUserBookshelfIsJoinBo;
import com.yibei.yb.domain.clientVo.YbUserBookshelfInfoVo;
import com.yibei.yb.domain.clientVo.YbUserBookshelfIsJoinVo;
import com.yibei.yb.domain.clientVo.YbUserBookshelfQbInfoVo;
import com.yibei.yb.domain.clientVo.YbUserBookshelfTmInfoVo;
import com.yibei.yb.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Client_UserBookshelfController")
@Api(value = "用户书架模块",tags = "用户书架模块")
@RequestMapping("/client/userBookshelf")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserBookshelfController extends BaseController {

    private final IYbUserBookshelfService iYbUserBookshelfService;

    private final IYbQuestionBankService iYbQuestionBankService;

    private final IYbTeachingMaterialService iYbTeachingMaterialService;

    private final IYbTeachingMaterialEntryService iYbTeachingMaterialEntryService;

    private final IYbStudyConfigService iYbStudyConfigService;

    private final ISysConfigService iSysConfigService;

    private final IYbQuestionBankContentService iYbQuestionBankContentService;

    private final IYbUserCollectionService iYbUserCollectionService;

    private final IYbTeachingMaterialLogService iYbTeachingMaterialLogService;

    private final IYbAnswerLogService iYbAnswerLogService;

    private final IYbNoteService iYbNoteService;

    @ApiOperation("书架列表")
    @PostMapping("/list")
    @LoginChecked
    public TableDataInfo<YbUserBookshelfInfoVo> list(@RequestBody PageBo bo) {

        Long userId = getUserId();
        Page<YbUserBookshelfInfoVo> page = iYbUserBookshelfService.getListInfo(bo,getUserId());
        //用户未设置时，采用后台配置的学习信息
        SysConfig ratioConfig = iSysConfigService.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey,"study_review_ratio").last("LIMIT 1"));
        SysConfig numConfig = iSysConfigService.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey,"study_entry_num").last("LIMIT 1"));

        //相关数据
        page.getRecords().forEach(item->{
            if(item.getContentType().equals(1l)){
                //【教材】
                YbUserBookshelfTmInfoVo tmInfo = new YbUserBookshelfTmInfoVo();
                // 教材词条数
                long entryNum = iYbTeachingMaterialEntryService.count(new LambdaQueryWrapper<YbTeachingMaterialEntry>()
                        .eq(YbTeachingMaterialEntry::getStatus,1)
                        .gt(YbTeachingMaterialEntry::getParentId,0)
                        .eq(YbTeachingMaterialEntry::getTeachingMaterialId,item.getId()));
                tmInfo.setEntryNum(entryNum);

                //是否有背诵计划（0没有，1有）
                long isReciteNum = iYbTeachingMaterialEntryService.getIsReciteNum(item.getId(),userId);
                tmInfo.setIsRecite(isReciteNum>0?1:0);

                //已读百分比
                long proportionNum = iYbTeachingMaterialEntryService.getProportionNum(item.getId(),userId);
                tmInfo.setProportion(CalculationUtils.division(proportionNum,entryNum));

                //今日需背诵词条数
                YbStudyConfig studyConfig = iYbStudyConfigService.getOne(new LambdaQueryWrapper<YbStudyConfig>()
                        .eq(YbStudyConfig::getTeachingMaterialId,item.getId())
                        .eq(YbStudyConfig::getUserId,userId)
                        .last("LIMIT 1"));
                tmInfo.setReciteNum(studyConfig!=null?getQuantity(studyConfig.getQuantity(),ratioConfig.getConfigValue()):getQuantity(numConfig.getConfigValue(),ratioConfig.getConfigValue()));

                item.setTmInfo(tmInfo);
            }else if(item.getContentType().equals(2l)){
                //【题库】
                YbUserBookshelfQbInfoVo qbInfo = new YbUserBookshelfQbInfoVo();
                //总题数
                long allNum = iYbQuestionBankContentService.count(new LambdaQueryWrapper<YbQuestionBankContent>()
                        .eq(YbQuestionBankContent::getStatus,1)
                        .gt(YbQuestionBankContent::getParentId,0)
                        .eq(YbQuestionBankContent::getQuestionBankId,item.getId()));
                qbInfo.setAllNum(allNum);

                //已做题数
                long doneNum = iYbQuestionBankContentService.getAnswersNum(item.getId(),userId,null);
                qbInfo.setDoneNum(doneNum);

                //完成百分比
                qbInfo.setCompletedPercentage(CalculationUtils.division(doneNum,allNum));

                item.setQbInfo(qbInfo);
            }
        });

        return PageUtils.buildDataInfo(page.getRecords(),page.getTotal());
    }

    @ApiOperation("加入/移出书架")
    @PostMapping("/bookshelf")
    @LoginChecked
    @Transactional
    public AjaxResult bookshelf(@RequestBody YbUserBookshelfAddBo bo) {

        if(bo.getContentType()==1){
            //教材
            YbTeachingMaterial teachingMaterial = iYbTeachingMaterialService.getById(bo.getContentId());
            if(teachingMaterial == null){
                return AjaxResult.error("教材不存在");
            }
        }else if(bo.getContentType()==2){
            //题库
            YbQuestionBank questionBank = iYbQuestionBankService.getById(bo.getContentId());
            if(questionBank == null){
                return AjaxResult.error("题库不存在");
            }
        }else{
            return AjaxResult.error("类型错误");
        }

        YbUserBookshelf ybUserBookshelf = iYbUserBookshelfService.getOne(new LambdaQueryWrapper<YbUserBookshelf>()
                .eq(YbUserBookshelf::getContentType,bo.getContentType())
                .eq(YbUserBookshelf::getContentId,bo.getContentId())
                .eq(YbUserBookshelf::getUserId,getUserId())
                .last("LIMIT 1"));

        int operationType = bo.getOperationType();//操作类型 1加入书架,2移出书架
        switch (operationType){
            case 1:
                //加入书架
                if(ybUserBookshelf!=null){
                    return AjaxResult.error("请勿重复添加书架");
                }

                YbUserBookshelf userBookshelf = new YbUserBookshelf();
                userBookshelf.setUserId(getUserId());
                userBookshelf.setContentType(bo.getContentType());
                userBookshelf.setContentId(bo.getContentId());
                userBookshelf.setLastOpenTime(TimeUtils.getCurrentDate());

                if(!iYbUserBookshelfService.save(userBookshelf)){
                    return AjaxResult.error("添加书架失败");
                }
                return AjaxResult.success("添加书架成功");
            case 2:
                //移出书架
                if(ybUserBookshelf == null){
                    return AjaxResult.error("还未添加书架");
                }

                UpdateWrapper uw = new UpdateWrapper();
                uw.eq("id",ybUserBookshelf.getId());
                uw.setSql("is_deleted=1");

                if(!iYbUserBookshelfService.update(uw)){
                    return AjaxResult.error("移出书架失败");
                }

                //清除添加的子内容
                UpdateWrapper uwc = new UpdateWrapper();
                uwc.eq("content_type",ybUserBookshelf.getContentType());
                uwc.eq("content_id",ybUserBookshelf.getContentId());
                uwc.eq("user_id",getUserId());
                uwc.setSql("is_deleted=1");
                iYbUserCollectionService.update(uwc);

                if(ybUserBookshelf.getContentType().equals(1L)){
                    //清除教材阅读背诵记录
                    UpdateWrapper uwl = new UpdateWrapper();
                    uwl.eq("teaching_material_id",ybUserBookshelf.getContentId());
                    uwl.eq("user_id",getUserId());
                    uwl.setSql("is_deleted=1");
                    iYbTeachingMaterialLogService.update(uwl);
                    //清除教材学习配置
                    UpdateWrapper uws = new UpdateWrapper();
                    uws.eq("teaching_material_id",ybUserBookshelf.getContentId());
                    uws.eq("user_id",getUserId());
                    uws.setSql("is_deleted=1");
                    iYbStudyConfigService.update(uws);
                }else if(ybUserBookshelf.getContentType().equals(2L)){
                    //清除答题记录
                    UpdateWrapper uwa = new UpdateWrapper();
                    uwa.eq("question_bank_id",ybUserBookshelf.getContentId());
                    uwa.eq("user_id",getUserId());
                    uwa.setSql("is_deleted=1");
                    iYbAnswerLogService.update(uwa);
                }

                return AjaxResult.success("移出书架成功");
            default:
                return AjaxResult.error("操作错误");
        }
    }

    @ApiOperation("查询教材/题库是否加入书架")
    @PostMapping("/isBookshelf")
    @LoginChecked
    public AjaxResult<YbUserBookshelfIsJoinVo> isBookshelf(@RequestBody YbUserBookshelfIsJoinBo bo) {

        if(bo.getContentType().equals(1l)){
            //教材
            YbTeachingMaterial teachingMaterial = iYbTeachingMaterialService.getById(bo.getId());
            if(teachingMaterial == null){
                return AjaxResult.error("教材不存在",null);
            }
        }else if(bo.getContentType().equals(2l)){
            //题库
            YbQuestionBank questionBank = iYbQuestionBankService.getById(bo.getId());
            if(questionBank == null){
                return AjaxResult.error("题库不存在",null);
            }
        }else{
            return AjaxResult.error("类型错误",null);
        }

        YbUserBookshelfIsJoinVo vo = new YbUserBookshelfIsJoinVo();
        vo.setIsBookshelf(iYbUserBookshelfService.getIsBookshelf(getUserId(),bo.getContentType(),bo.getId()));

        return AjaxResult.success(vo);
    }

    /**
     * 总数=学习数加复习数(学习数*倍率)
     * 学习数：num , 倍率：ratio
     * */
    private int getQuantity(Object num,Object ratio){
        int quantity = 0;
        try {
            int numi = Integer.parseInt(num.toString());
            int ratioi = Integer.parseInt(ratio.toString());
            quantity = numi + numi*ratioi;
        }catch (Exception e){
            e.printStackTrace();
        }
        return quantity;
    }

}
