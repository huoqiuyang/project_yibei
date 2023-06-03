package com.yibei.client.controller.yb;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.BeanCopyUtils;
import com.yibei.common.utils.CalculationUtils;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.utils.TimeUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.yb.mapper.EntryMapper;
import com.yibei.system.domain.SysConfig;
import com.yibei.system.domain.clientBo.IdBo;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.system.service.ISysConfigService;
import com.yibei.yb.domain.*;
import com.yibei.yb.domain.clientBo.*;
import com.yibei.yb.domain.clientVo.*;
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

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@RestController("Client_TeachingMaterialController")
@Api(value = "教材模块",tags = "教材模块")
@RequestMapping("/client/teachingMaterial")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class TeachingMaterialController extends BaseController {

    private final IYbTeachingMaterialService iYbTeachingMaterialService;

    private final IYbTeachingMaterialEntryService iYbTeachingMaterialEntryService;

    private final IYbUserCollectionService iYbUserCollectionService;

    private final IYbStudyConfigService iYbStudyConfigService;

    private final ISysConfigService iSysConfigService;

    private final IYbUserBookshelfService iYbUserBookshelfService;

    private final IYbNoteService iYbNoteService;

    private final IYbTeachingMaterialLogService iYbTeachingMaterialLogService;

    private final IYbContentLinkService iYbContentLinkService;

    @Resource
    private EntryMapper entryMapper;

    @ApiOperation("教材列表")
    @PostMapping("/list")
    public TableDataInfo<YbTeachingMaterialListVo> list(@RequestBody PageBo bo) {

        LambdaQueryWrapper<YbTeachingMaterial> lqw = new LambdaQueryWrapper<>();
        lqw.eq(YbTeachingMaterial::getStatus,1);
        lqw.orderByAsc(YbTeachingMaterial::getSort);
        lqw.orderByDesc(YbTeachingMaterial::getCreateTime);

        Page<YbTeachingMaterial> page = iYbTeachingMaterialService.page(new Page<>(bo.getPageNum(), bo.getPageSize()), lqw);
        List<YbTeachingMaterialListVo> listVo = BeanCopyUtils.listCopy(page.getRecords(),new CopyOptions(),YbTeachingMaterialListVo.class);

        Long userId = getUserId();
        //用户未设置时，采用后台配置的学习信息
        SysConfig ratioConfig = iSysConfigService.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey,"study_review_ratio").last("LIMIT 1"));
        SysConfig numConfig = iSysConfigService.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey,"study_entry_num").last("LIMIT 1"));

        listVo.forEach(item->{

            //教材词条数
            long entryNum = iYbTeachingMaterialEntryService.count(new LambdaQueryWrapper<YbTeachingMaterialEntry>()
                    .eq(YbTeachingMaterialEntry::getStatus,1)
                    .gt(YbTeachingMaterialEntry::getParentId,0)
                    .eq(YbTeachingMaterialEntry::getTeachingMaterialId,item.getId()));
            item.setEntryNum(entryNum);

            if(userId!=null && !userId.equals(0l)){
                //已读百分比
//                long proportionNum = iYbTeachingMaterialLogServic.count(new LambdaQueryWrapper<YbTeachingMaterialLog>()
//                        .eq(YbTeachingMaterialLog::getLearningType,1l)
//                        .eq(YbTeachingMaterialLog::getTeachingMaterialId,item.getId())
//                        .eq(YbTeachingMaterialLog::getUserId,userId));
                long proportionNum = iYbTeachingMaterialEntryService.getProportionNum(item.getId(),userId);
                item.setProportion(CalculationUtils.division(proportionNum,entryNum));

                //是否加入书架（0未加入书架，1已加入书架）
                long isBookshelf = iYbUserBookshelfService.count(new LambdaQueryWrapper<YbUserBookshelf>()
                        .eq(YbUserBookshelf::getUserId,userId)
                        .eq(YbUserBookshelf::getContentType,1)
                        .eq(YbUserBookshelf::getContentId,item.getId()));
                item.setIsBookshelf(isBookshelf>0?1:0);

                //是否有背诵计划（0没有，1有）
//                long isRecite = iYbUserCollectionService.count(new LambdaQueryWrapper<YbUserCollection>()
//                        .eq(YbUserCollection::getUserId,userId)
//                        .eq(YbUserCollection::getContentType,1)
//                        .eq(YbUserCollection::getContentId,item.getId()));
                long isReciteNum = iYbTeachingMaterialEntryService.getIsReciteNum(item.getId(),userId);
                item.setIsRecite(isReciteNum>0?1:0);

                //今日需背诵词条数
                YbStudyConfig studyConfig = iYbStudyConfigService.getOne(new LambdaQueryWrapper<YbStudyConfig>()
                        .eq(YbStudyConfig::getTeachingMaterialId,item.getId())
                        .eq(YbStudyConfig::getUserId,userId)
                        .last("LIMIT 1"));
//                item.setReciteNum(studyConfig!=null?getQuantity(studyConfig.getQuantity(),ratioConfig.getConfigValue()):getQuantity(numConfig.getConfigValue(),ratioConfig.getConfigValue()));//配置的学习量+复习量
                item.setReciteNum(studyConfig!=null?studyConfig.getQuantity().intValue():Integer.valueOf(numConfig.getConfigValue()));//
            }
        });

        return PageUtils.buildDataInfo(listVo,page.getTotal());
    }

    @ApiOperation("教材详情")
    @PostMapping("/detail")
    @LoginChecked
    public AjaxResult<YbTeachingMaterialInfoVo> detail(@RequestBody IdBo bo) {

        //验证会员，非会员用户不能进入详情页面
        YbUserVip ybUserVip = iUserService.getUserVipInfo(getUserId());
        if(ybUserVip==null){
            return AjaxResult.error("还不是会员，暂时无法查看",null);
        }

        YbTeachingMaterial teachingMaterial = iYbTeachingMaterialService.getById(bo.getId());
        if(teachingMaterial == null){
            return AjaxResult.error("教材不存在",null);
        }

        YbTeachingMaterialInfoVo vo = BeanCopyUtils.oneCopy(teachingMaterial, new CopyOptions(), YbTeachingMaterialInfoVo.class);

        //名词解释、简答、论述 词条数量
        long nounEntryNum = iYbTeachingMaterialEntryService.count(new LambdaQueryWrapper<YbTeachingMaterialEntry>()
                .eq(YbTeachingMaterialEntry::getStatus,1)
                .gt(YbTeachingMaterialEntry::getParentId,0)
                .eq(YbTeachingMaterialEntry::getLabel,"名词解释")
                .eq(YbTeachingMaterialEntry::getTeachingMaterialId,vo.getId()));
        vo.setNounEntryNum(nounEntryNum);
        long briefEntryNum = iYbTeachingMaterialEntryService.count(new LambdaQueryWrapper<YbTeachingMaterialEntry>()
                .eq(YbTeachingMaterialEntry::getStatus,1)
                .gt(YbTeachingMaterialEntry::getParentId,0)
                .eq(YbTeachingMaterialEntry::getLabel,"简答")
                .eq(YbTeachingMaterialEntry::getTeachingMaterialId,vo.getId()));
        vo.setBriefEntryNum(briefEntryNum);
        long discussEntryNum = iYbTeachingMaterialEntryService.count(new LambdaQueryWrapper<YbTeachingMaterialEntry>()
                .eq(YbTeachingMaterialEntry::getStatus,1)
                .gt(YbTeachingMaterialEntry::getParentId,0)
                .eq(YbTeachingMaterialEntry::getLabel,"论述")
                .eq(YbTeachingMaterialEntry::getTeachingMaterialId,vo.getId()));
        vo.setDiscussEntryNum(discussEntryNum);

        //是否加入书架（0未加入书架，1已加入书架）
        long isBookshelf = iYbUserBookshelfService.count(new LambdaQueryWrapper<YbUserBookshelf>()
                .eq(YbUserBookshelf::getUserId,getUserId())
                .eq(YbUserBookshelf::getContentType,1)
                .eq(YbUserBookshelf::getContentId,vo.getId()));
        vo.setIsBookshelf(isBookshelf>0?1:0);

        //是否有背诵计划（0没有，1有）
        long isReciteNum = iYbTeachingMaterialEntryService.getIsReciteNum(vo.getId(),getUserId());
        vo.setIsRecite(isReciteNum>0?1:0);

        //刷新最近打开时间
        iYbUserBookshelfService.updateLastTime(getUserId(),1l,vo.getId());

        return AjaxResult.success(vo);
    }

    @ApiOperation("词条详情-阅读模式")
    @PostMapping("/readDetail")
    @LoginChecked
    public AjaxResult<YbTeachingMaterialEntryReadInfoVo> readDetail(@RequestBody YbTeachingMaterialEntryReadInfoBo bo) {

        //验证会员，非会员用户不能进入详情页面
        YbUserVip ybUserVip = iUserService.getUserVipInfo(getUserId());
        if(ybUserVip==null){
            return AjaxResult.error("还不是会员，暂时无法查看",null);
        }

        //查询词条ID
        Long selectId = null;
        YbTeachingMaterialEntry teachingMaterialEntry = null;
        if(bo.getId()!=null){
            //跳转指定词条
            teachingMaterialEntry = iYbTeachingMaterialEntryService.getById(bo.getId());
        }else{
            //进入阅读，跳转此教材上一次打开的词条，从没打开过的跳转第一个词条
            teachingMaterialEntry = iYbTeachingMaterialEntryService.getEntryLog(bo.getTeachingMaterialId(),getUserId(),1);
        }
        if(teachingMaterialEntry!=null){
            selectId = teachingMaterialEntry.getId();
        }

        //查询词条信息
        YbTeachingMaterialEntryReadInfoVo vo = iYbTeachingMaterialEntryService.getEntryReadInfo(bo.getTeachingMaterialId(),selectId);
        if(vo==null){
            return AjaxResult.success("",null);
        }

        //查询笔记信息
        YbNote note = iYbNoteService.getOne(new LambdaQueryWrapper<YbNote>()
                .eq(YbNote::getEntryId,vo.getId())
                .eq(YbNote::getUserId,getUserId())
                .last("LIMIT 1"));
        if(note!=null){
            vo.setNote(note.getContent());
        }

        //查询当前词条 是否加入背诵
        YbUserCollection userCollection = iYbUserCollectionService.getOne(new LambdaQueryWrapper<YbUserCollection>()
                .eq(YbUserCollection::getContentType,1l)
                .eq(YbUserCollection::getEntryId,vo.getId())
                .eq(YbUserCollection::getUserId,getUserId())
                .last("LIMIT 1"));
        if(userCollection!=null){
            vo.setIsRecite(1);
        }

        //查询词条的相关链接信息
        List<YbContentLinkVo> ybContentLinkVoListst = iYbContentLinkService.listVo(new LambdaQueryWrapper<YbContentLink>()
                .eq(YbContentLink::getSourceType,1l)
                .eq(YbContentLink::getEntryId,vo.getId())
                .orderByDesc(YbContentLink::getCreateTime));
        vo.setYbContentLinkVoList(iYbContentLinkService.queryExtData(ybContentLinkVoListst));

        //添加阅读记录
        YbTeachingMaterialLog teachingMaterialLog = iYbTeachingMaterialLogService.getOne(new LambdaQueryWrapper<YbTeachingMaterialLog>()
                .eq(YbTeachingMaterialLog::getLearningType,1l)
                .eq(YbTeachingMaterialLog::getTeachingMaterialEntryId,vo.getId())
                .eq(YbTeachingMaterialLog::getUserId,getUserId())
                .last("LIMIT 1"));
        if(teachingMaterialLog!=null){
            teachingMaterialLog.setLastTime(TimeUtils.getCurrentDate());
            iYbTeachingMaterialLogService.updateById(teachingMaterialLog);
        }else{
            teachingMaterialLog = new YbTeachingMaterialLog();
            teachingMaterialLog.setTeachingMaterialId(vo.getTeachingMaterialId());
            teachingMaterialLog.setTeachingMaterialEntryId(vo.getId());
            teachingMaterialLog.setUserId(getUserId());
            teachingMaterialLog.setLearningType(1);
            teachingMaterialLog.setLastTime(TimeUtils.getCurrentDate());
            iYbTeachingMaterialLogService.save(teachingMaterialLog);
        }

        //刷新最近打开时间
        iYbUserBookshelfService.updateLastTime(getUserId(),1l,vo.getTeachingMaterialId());

        return AjaxResult.success(vo);
    }

    /*@ApiOperation("词条详情-背诵模式")
    @PostMapping("/reciteDetail")
    @LoginChecked
    public AjaxResult<Map<String, Object>> reciteDetail(@RequestBody YbTeachingMaterialEntryReciteInfoBo bo) {

        //验证会员，非会员用户不能进入详情页面
        YbUserVip ybUserVip = iUserService.getUserVipInfo(getUserId());
        if(ybUserVip==null){
            return AjaxResult.error("还不是会员，暂时无法查看",null);
        }

        //验证该教材是否存在
        YbTeachingMaterial teachingMaterial = iYbTeachingMaterialService.getById(bo.getTeachingMaterialId());
        if(teachingMaterial == null){
            return AjaxResult.error("教材不存在",null);
        }

        Map<String, Object> mapVo = new HashMap<>(2);

        YbTeachingMaterialEntryReciteInfoVo vo = null;
        int isLast = 0;//是否是最后一个词条

        //获取学习配置
        Map<String,Integer> map = iSysConfigService.getStudyConfig(getUserId(),bo.getTeachingMaterialId());

        //获取词条信息（根据背诵计划获取当天需要背诵的词条）
        Integer studyNum = map.get("studyNum");
        Integer review = map.get("review");
        List<YbTeachingMaterialEntryReciteInfoVo> list = iYbTeachingMaterialEntryService.getToDayRecitePlanList(getUserId(),bo.getTeachingMaterialId(),studyNum,review);
        if(list==null || list.size()<1){
            return AjaxResult.error("无背诵计划",null);
        }

        list.forEach(item->{
            //查询此词条当天背诵情况
            YbTeachingMaterialLog entryTodayLog = iYbTeachingMaterialLogService.getOne(new LambdaQueryWrapper<YbTeachingMaterialLog>()
                    .eq(YbTeachingMaterialLog::getTeachingMaterialId,item.getTeachingMaterialId())
                    .eq(YbTeachingMaterialLog::getTeachingMaterialEntryId,item.getEntryId())
                    .eq(YbTeachingMaterialLog::getUserId,getUserId())
                    .eq(YbTeachingMaterialLog::getLearningType,2)
                    .ge(YbTeachingMaterialLog::getLastTime,TimeUtils.getCurrentDate("yyyy-MM-dd 00:00:00"))
                    .last("LIMIT 1"));
            item.setReciteStatus(entryTodayLog!=null?entryTodayLog.getStatus():0);
            item.setLastTime(entryTodayLog!=null?entryTodayLog.getLastTime():null);
        });

        //获取当天最近一次背诵的词条
        YbTeachingMaterialLog teachingMaterialLog = iYbTeachingMaterialLogService.getOne(new LambdaQueryWrapper<YbTeachingMaterialLog>()
                .eq(YbTeachingMaterialLog::getTeachingMaterialId,bo.getTeachingMaterialId())
                .eq(YbTeachingMaterialLog::getUserId,getUserId())
                .eq(YbTeachingMaterialLog::getLearningType,2)
                .ge(YbTeachingMaterialLog::getLastTime,TimeUtils.getCurrentDate("yyyy-MM-dd 00:00:00"))
                .orderByDesc(YbTeachingMaterialLog::getLastTime)
                .last("LIMIT 1"));

        //今日计划已答数量
        List<YbTeachingMaterialLog> countsList = iYbTeachingMaterialLogService.list(new LambdaQueryWrapper<YbTeachingMaterialLog>()
                .eq(YbTeachingMaterialLog::getTeachingMaterialId,bo.getTeachingMaterialId())
                .eq(YbTeachingMaterialLog::getUserId,getUserId())
                .eq(YbTeachingMaterialLog::getLearningType,2)
                .inSql(YbTeachingMaterialLog::getStatus,"1,2")
                .ge(YbTeachingMaterialLog::getLastTime,TimeUtils.getCurrentDate("yyyy-MM-dd 00:00:00")));
        long counts = countsList.size();
        //当日需要背诵的总词条数
        int size = list.size();
        //避免当天已背诵后被移出背诵导致的总数不对，现将当天已背诵的却没在最新背诵列表的数量加上去
        for(YbTeachingMaterialLog log : countsList){
            YbTeachingMaterialEntryReciteInfoVo bean = list.stream().filter(s -> s.getEntryId().equals(log.getTeachingMaterialEntryId())).findFirst().orElse(null);
            if(bean==null){
                size = size + 1;
            }
        }

        //今日计划已答【知道】数量
        long knowNum = iYbTeachingMaterialLogService.count(new LambdaQueryWrapper<YbTeachingMaterialLog>()
                .eq(YbTeachingMaterialLog::getTeachingMaterialId,bo.getTeachingMaterialId())
                .eq(YbTeachingMaterialLog::getUserId,getUserId())
                .eq(YbTeachingMaterialLog::getLearningType,2)
                .eq(YbTeachingMaterialLog::getStatus,1)
                .ge(YbTeachingMaterialLog::getLastTime,TimeUtils.getCurrentDate("yyyy-MM-dd 00:00:00")));

        if(bo.getOperationType() == 0){
            //默认打开背诵：没打开过就获取第一个，打开过就获取最近打开的那个词条
            if(teachingMaterialLog!=null){
                //今日有记录
                for(int i = 0;i<list.size();i++){
                    if(teachingMaterialLog.getTeachingMaterialEntryId().equals(list.get(i).getEntryId())){
                        vo = list.get(i);
                        break;
                    }
                }
            }
        }else{
            //跳下一个词条
            List<YbTeachingMaterialEntryReciteInfoVo> nowList = null;
            if(teachingMaterialLog!=null){
                if(counts >= size){
                    //答完一遍后，筛选回答[不知道]的列表
                    nowList = list.stream().filter(a->a.getReciteStatus()==2).collect(Collectors.toList());//.filter(a->!teachingMaterialLog.getTeachingMaterialEntryId().equals(a.getEntryId()))
                    nowList.sort(Comparator.comparing(YbTeachingMaterialEntryReciteInfoVo::getLastTime));
                }else{
                    //未答完一遍，筛选未回答的列表
                    nowList = list.stream().filter(a->a.getReciteStatus()==0).collect(Collectors.toList());//.filter(a->!teachingMaterialLog.getTeachingMaterialEntryId().equals(a.getEntryId()))
                }

                if(nowList!=null && nowList.size()>0){
                    vo = nowList.get(0);
                }
            }
        }

        if(vo==null){
            vo = list.get(0);
        }

        if(knowNum+1>=list.size()){
            isLast = 1;
        }


        mapVo.put("size",size);
        mapVo.put("is_last",isLast);
        mapVo.put("num",knowNum);

        *//*

        //根据情景返回相应的词条信息
        if(teachingMaterialLog!=null){
            for(int i = 0;i<list.size();i++){
                //当天最新背诵记录的词条
                if(teachingMaterialLog.getTeachingMaterialEntryId().equals(list.get(i).getEntryId())){
                    if(bo.getOperationType() == 1){
                        //进入下一个背诵词条
                        if(i+1==list.size()-1){
                            //下一词条为最后一词条
                            mapVo.put("is_last",1);
                            mapVo.put("num",i+2);
                            vo = list.get(i+1);
                        }else if(i+1>list.size()-1){
                            //没有下一词条，正常情况下不会进来，为避免报错本次显示最后一词条
                            mapVo.put("is_last",1);
                            mapVo.put("num",i+1);
                            vo = list.get(i);
                        }else{
                            mapVo.put("num",i+2);
                            vo = list.get(i+1);
                        }
                    }else{
                        //直接打开背诵（进入第一个词条或当天最近打开的词条）
                        vo = list.get(i);
                        if(i==list.size()-1){
                            //本次词条为最后一词条
                            mapVo.put("num",i+1);
                            mapVo.put("is_last",1);
                        }
                    }
                    break;
                }
            }
        }
        if(vo == null){
            vo = list.get(0);
        }
        //如果只有一条背诵计划，显示第一条时，也是最后一条
        if(list.size()<2){
            mapVo.put("is_last",1);
        }

         *//*

        //查询词条的相关链接信息
        List<YbContentLinkVo> ybContentLinkVoListst = iYbContentLinkService.listVo(new LambdaQueryWrapper<YbContentLink>()
                .eq(YbContentLink::getSourceType,1l)
                .eq(YbContentLink::getEntryId,vo.getEntryId())
                .orderByDesc(YbContentLink::getCreateTime));
        vo.setYbContentLinkVoList(iYbContentLinkService.queryExtData(ybContentLinkVoListst));

        //查询笔记信息
        YbNote note = iYbNoteService.getOne(new LambdaQueryWrapper<YbNote>()
                .eq(YbNote::getEntryId,vo.getEntryId())
                .eq(YbNote::getUserId,getUserId())
                .last("LIMIT 1"));
        if(note!=null){
            vo.setNote(note.getContent());
        }

        mapVo.put("data",vo);

        //更新背诵记录
        editLog(getUserId(),vo.getEntryId(),vo.getTeachingMaterialId(),null);

        return AjaxResult.success(mapVo);
    }*/

    @ApiOperation("词条详情-背诵模式")
    @PostMapping("/reciteDetail")
    @LoginChecked
    public synchronized AjaxResult<Map<String, Object>> reciteDetail(@RequestBody YbTeachingMaterialEntryReciteInfoBo bo) {

        //验证会员，非会员用户不能进入详情页面
        YbUserVip ybUserVip = iUserService.getUserVipInfo(getUserId());
        if(ybUserVip==null){
            return AjaxResult.error("还不是会员，暂时无法查看",null);
        }

        //验证该教材是否存在
        YbTeachingMaterial teachingMaterial = iYbTeachingMaterialService.getById(bo.getTeachingMaterialId());
        if(teachingMaterial == null){
            return AjaxResult.error("教材不存在",null);
        }

        Map<String, Object> mapVo = new HashMap<>(2);

        YbTeachingMaterialEntryReciteInfoVo vo = null;

        //获取学习配置
        Map<String,Integer> map = iSysConfigService.getStudyConfig(getUserId(),bo.getTeachingMaterialId());

        //获取词条信息（根据背诵计划获取当天需要背诵的词条）
        Integer studyNum = map.get("studyNum");
        Integer review = map.get("review");
        List<YbTeachingMaterialEntryReciteInfoVo> list = iYbTeachingMaterialEntryService.getToDayRecitePlanList(getUserId(),bo.getTeachingMaterialId(),studyNum,review);
        if(list==null || list.size()<1){
            return AjaxResult.error("无背诵计划",null);
        }

        list.forEach(item->{
            //查询此词条当天背诵情况
            YbTeachingMaterialLog entryTodayLog = iYbTeachingMaterialLogService.getOne(new LambdaQueryWrapper<YbTeachingMaterialLog>()
                    .eq(YbTeachingMaterialLog::getTeachingMaterialId,item.getTeachingMaterialId())
                    .eq(YbTeachingMaterialLog::getTeachingMaterialEntryId,item.getEntryId())
                    .eq(YbTeachingMaterialLog::getUserId,getUserId())
                    .eq(YbTeachingMaterialLog::getLearningType,2)
                    .ge(YbTeachingMaterialLog::getLastTime, TimeUtils.getCurrentDate("yyyy-MM-dd 00:00:00"))
                    .last("LIMIT 1"));
            if(entryTodayLog!=null){
                item.setReciteStatus(entryTodayLog.getStatus());
                item.setLastTime(entryTodayLog.getLastTime());
                item.setIsToday(1);
            }else{
                item.setReciteStatus(0);
                item.setLastTime(null);
                item.setIsToday(0);
            }
        });

        //当日需要背诵的总词条数
        int size = list.size();

        //当日背诵后[已知道]的词条数
        int knowNum = list.stream().filter(a -> a.getReciteStatus()==1).collect(Collectors.toList()).size();

        //获取词条详情
        //1、优先还没有回答的
        List<YbTeachingMaterialEntryReciteInfoVo> nowList = list.stream().filter(a->a.getReciteStatus()==0).collect(Collectors.toList());
        if(nowList==null || nowList.size()==0){
            //2、全部回答后，根据回答时间正序回答[不知道]的词条
            nowList = list.stream().filter(a->a.getReciteStatus()==2).collect(Collectors.toList());
            nowList.sort(Comparator.comparing(YbTeachingMaterialEntryReciteInfoVo::getLastTime));
        }
        if(nowList!=null && nowList.size()>0){
            vo = nowList.get(0);
        }

        if(vo==null){
            vo = list.get(0);
        }

        //查询词条的相关链接信息
        List<YbContentLinkVo> ybContentLinkVoListst = iYbContentLinkService.listVo(new LambdaQueryWrapper<YbContentLink>()
                .eq(YbContentLink::getSourceType,1l)
                .eq(YbContentLink::getEntryId,vo.getEntryId())
                .orderByDesc(YbContentLink::getCreateTime));
        vo.setYbContentLinkVoList(iYbContentLinkService.queryExtData(ybContentLinkVoListst));

        //查询笔记信息
        YbNote note = iYbNoteService.getOne(new LambdaQueryWrapper<YbNote>()
                .eq(YbNote::getEntryId,vo.getEntryId())
                .eq(YbNote::getUserId,getUserId())
                .last("LIMIT 1"));
        if(note!=null){
            vo.setNote(note.getContent());
        }

        int isLast = 0;//是否是最后一个词条
        if(knowNum+1>=list.size()){
            isLast = 1;
        }

        mapVo.put("size",size);
        mapVo.put("is_last",isLast);
        mapVo.put("num",knowNum);
        mapVo.put("data",vo);

        //更新背诵记录（今日未答词条，由于要刷新更新时间，所以背诵状态值要重置）
        Integer result = vo.getReciteStatus();
        if(vo.getIsToday()==0){
            result = 0;
        }
        editLog(getUserId(),vo.getEntryId(),vo.getTeachingMaterialId(),result);

        return AjaxResult.success(mapVo);
    }

    @ApiOperation("更新背诵词条结果")
    @PostMapping("/editReciteResult")
    @LoginChecked
    public AjaxResult editReciteResult(@RequestBody ReciteResultBo bo) {

        YbTeachingMaterialEntry teachingMaterialEntry = iYbTeachingMaterialEntryService.getById(bo.getEntryId());
        if(teachingMaterialEntry==null){
            return AjaxResult.error("词条不存在");
        }

        //更新背诵记录的结果
        editLog(getUserId(),teachingMaterialEntry.getId(),teachingMaterialEntry.getTeachingMaterialId(),bo.getResult());

        return AjaxResult.success("提交成功");
    }

    /**
     * 更新背诵记录
     * */
    private void editLog(Long userId,Long entryId,Long teachingMaterialId,Integer result){

        YbTeachingMaterialLog teachingMaterialLog = iYbTeachingMaterialLogService.getOne(new LambdaQueryWrapper<YbTeachingMaterialLog>()
                .eq(YbTeachingMaterialLog::getLearningType,2l)
                .eq(YbTeachingMaterialLog::getTeachingMaterialEntryId,entryId)
                .eq(YbTeachingMaterialLog::getUserId,userId)
//                .ge(YbTeachingMaterialLog::getLastTime,TimeUtils.getCurrentDate("yyyy-MM-dd 00:00:00"))
                .last("LIMIT 1"));
        if(teachingMaterialLog!=null){
            teachingMaterialLog.setLastTime(TimeUtils.getCurrentDate());
//            if(result!=null && result>0){
                teachingMaterialLog.setStatus(result);
//            }
            iYbTeachingMaterialLogService.updateById(teachingMaterialLog);
        }else{
            teachingMaterialLog = new YbTeachingMaterialLog();
            teachingMaterialLog.setTeachingMaterialId(teachingMaterialId);
            teachingMaterialLog.setTeachingMaterialEntryId(entryId);
            teachingMaterialLog.setUserId(userId);
            teachingMaterialLog.setLearningType(2);
            teachingMaterialLog.setLastTime(TimeUtils.getCurrentDate());
            iYbTeachingMaterialLogService.save(teachingMaterialLog);
        }

        //刷新最近打开时间
        iYbUserBookshelfService.updateLastTime(getUserId(),1l,teachingMaterialId);
    }


    @ApiOperation("教材目录列表")
    @PostMapping("/catalogueList")
    public AjaxResult catalogueList(@RequestBody YbUserCollectionInfoBo bo) {
        Long userId = getUserId();

        List<YbTeachingMaterialEntry> list = iYbTeachingMaterialEntryService.list(new LambdaQueryWrapper<YbTeachingMaterialEntry>()
                .eq(YbTeachingMaterialEntry::getTeachingMaterialId,bo.getId())
                .eq(YbTeachingMaterialEntry::getStatus,1)
                .eq(YbTeachingMaterialEntry::getParentId,0)
                .orderByAsc(YbTeachingMaterialEntry::getSort).orderByDesc(YbTeachingMaterialEntry::getCreateTime));
        List<YbTmeCatalogueInfoVo> listVo = BeanCopyUtils.listCopy(list,new CopyOptions(), YbTmeCatalogueInfoVo.class);

        listVo.forEach(item->{
            item.setSecondList(iYbTeachingMaterialEntryService.getSecondList(userId==null?0l:userId,item.getId(),0));
        });

        return AjaxResult.success(listVo);
    }

    @ApiOperation("我的背诵列表")
    @PostMapping("/collectionList")
    @LoginChecked
    public AjaxResult collectionList(@RequestBody YbUserCollectionInfoBo bo) {
        List<YbTmeCatalogueInfoVo> listVo = iYbTeachingMaterialEntryService.getCollectionList(getUserId(),bo.getId());

        listVo.forEach(item->{
            item.setSecondList(iYbTeachingMaterialEntryService.getSecondList(getUserId(),item.getId(),1));
        });

        return AjaxResult.success(listVo);
    }

    @ApiOperation("加入背诵")
    @PostMapping("/joinRecitation")
    @LoginChecked
    public AjaxResult joinRecitation(@RequestBody YbUserCollectionAddBo bo) {

        List<Long> oriList = new ArrayList<>(Arrays.asList(bo.getIds()));
        // 校验词条是否存在
        List<YbTeachingMaterialEntry> ids = entryMapper.searchIdByIds(oriList);
        if (ids.size() != bo.getIds().length) {
            return AjaxResult.error("所选的词条中有不存在的词条");
        }
        Long userId = getUserId();
        // 教材题库类型
        Integer contentType = 1;
        //操作类型 1加入背诵2,移出背诵
        int operationType = bo.getOperationType();
        // 查出已经加入背诵的词条
        List<Long> alreadyAddList = entryMapper.getAddListByUserId(userId, contentType);
        switch (operationType){
            case 1:
                //加入背诵
                // 过滤掉已经加入的背诵的
                List<Long> preAddList = oriList.stream().filter(e -> !alreadyAddList.contains(e)).collect(Collectors.toList());
                if (preAddList.size() == 0) {
                    return AjaxResult.error("所选的词条已加入背诵");
                }
                // 保存背诵词条
                if(entryMapper.saveRecite(preAddList, ids.get(0).getTeachingMaterialId(), userId) == 0){
                    return AjaxResult.error("加入背诵失败");
                }
                return AjaxResult.success("加入背诵成功");
            case 2:
                //移出背诵
                List<Long> readyToMoveList = oriList.stream().filter(alreadyAddList :: contains).collect(Collectors.toList());
                if (readyToMoveList.size() == 0) {
                    return AjaxResult.error("所选词条尚未加入背诵");
                }
                // 更新
                if(entryMapper.remove(readyToMoveList) == 0){
                    return AjaxResult.error("移出背诵失败");
                }
                return AjaxResult.success("移出背诵成功");
            default:
                return AjaxResult.error("操作错误");
        }

    }

    @ApiOperation("教材每天学习信息获取")
    @PostMapping("/studyInfo")
    @LoginChecked
    public AjaxResult<StudyInfoVo> studyInfo(@RequestBody StudyInfoBo bo) {

        StudyInfoVo vo = new StudyInfoVo();

        //教材信息
        YbTeachingMaterial teachingMaterial = iYbTeachingMaterialService.getById(bo.getId());
        if(teachingMaterial==null){
            return AjaxResult.error("教材不存在",null);
        }
        //教材词条数
        /*int entryNum = (int)iYbTeachingMaterialEntryService.count(new LambdaQueryWrapper<YbTeachingMaterialEntry>()
                .eq(YbTeachingMaterialEntry::getStatus,1)
                .gt(YbTeachingMaterialEntry::getParentId,0)
                .eq(YbTeachingMaterialEntry::getTeachingMaterialId,teachingMaterial.getId()));*/

        //教材-用户添加到背诵的词条数
       /* int entryNum = (int)iYbUserCollectionService.count(new LambdaQueryWrapper<YbUserCollection>()
                .eq(YbUserCollection::getContentType,1)
                .eq(YbUserCollection::getContentId,teachingMaterial.getId())
                .eq(YbUserCollection::getUserId,getUserId()));*/
        int entryNum = iYbTeachingMaterialEntryService.getReciteEntryList(getUserId(),bo.getId());


        //用户未设置时，采用后台配置的学习信息
        SysConfig ratioConfig = iSysConfigService.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey,"study_review_ratio").last("LIMIT 1"));
        YbStudyConfig studyConfig = iYbStudyConfigService.getOne(new LambdaQueryWrapper<YbStudyConfig>()
                .eq(YbStudyConfig::getTeachingMaterialId,bo.getId())
                .eq(YbStudyConfig::getUserId,getUserId())
                .last("LIMIT 1"));
        if(studyConfig!=null){
            vo.setStudyNum(Integer.parseInt(studyConfig.getQuantity().toString()));
            vo.setReviewNum(vo.getStudyNum()*Integer.parseInt(ratioConfig.getConfigValue()));
            vo.setAllQuantity(getQuantity(studyConfig.getQuantity(),ratioConfig.getConfigValue()));
            vo.setIsVoice(studyConfig.getIsVoice());
        }else{
            SysConfig numConfig = iSysConfigService.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey,"study_entry_num").last("LIMIT 1"));
            //总数=学习数加复习数(学习数*倍率)
            vo.setStudyNum(Integer.parseInt(numConfig.getConfigValue()));
            vo.setReviewNum(vo.getStudyNum()*Integer.parseInt(ratioConfig.getConfigValue()));
            vo.setAllQuantity(getQuantity(numConfig.getConfigValue(),ratioConfig.getConfigValue()));
        }

        if(vo.getAllQuantity()>0){
            int day = (int) Math.ceil((float)entryNum/(float)vo.getStudyNum());
            //加9天的意思：目前设置的最大复习天数为10天，最后一个词学完需要day天，所以最后一个词复习完需要day+10-1天（减一天是因为第一天是学习日）
            vo.setDay(day+9);
        }else{
            vo.setDay(0);
        }

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

    @ApiOperation("教材每天学习信息配置")
    @PostMapping("/studyConfig")
    @LoginChecked
    public AjaxResult studyConfig(@RequestBody StudyConfigBo bo) {

        //教材信息
        YbTeachingMaterial teachingMaterial = iYbTeachingMaterialService.getById(bo.getId());
        if(teachingMaterial==null){
            return AjaxResult.error("教材不存在");
        }

        YbStudyConfig studyConfig = iYbStudyConfigService.getOne(new LambdaQueryWrapper<YbStudyConfig>()
                .eq(YbStudyConfig::getTeachingMaterialId,bo.getId())
                .eq(YbStudyConfig::getUserId,getUserId())
                .last("LIMIT 1"));
        if(studyConfig!=null){
            //修改配置
            if(bo.getQuantity()!=null){
                studyConfig.setQuantity(bo.getQuantity());
            }
            if(bo.getIsVoice()!=null){
                studyConfig.setIsVoice(bo.getIsVoice());
            }
            if(!iYbStudyConfigService.updateById(studyConfig)){
                return AjaxResult.error("配置失败");
            }
        }else{
            //新增配置
            studyConfig = new YbStudyConfig();
            studyConfig.setQuantity(bo.getQuantity());
            studyConfig.setIsVoice(bo.getIsVoice());
            studyConfig.setTeachingMaterialId(teachingMaterial.getId());
            studyConfig.setUserId(getUserId());
            if(!iYbStudyConfigService.save(studyConfig)){
                return AjaxResult.error("配置失败");
            }
        }

        return AjaxResult.success("配置成功");
    }

    @ApiOperation("编辑笔记")
    @PostMapping("/editNote")
    @LoginChecked
    public AjaxResult editNote(@RequestBody YbNoteInfoBo bo) {

        YbTeachingMaterialEntry teachingMaterialEntry = iYbTeachingMaterialEntryService.getById(bo.getEntryId());
        if(teachingMaterialEntry==null){
            return AjaxResult.error("词条不存在");
        }

        YbNote note = iYbNoteService.getOne(new LambdaQueryWrapper<YbNote>()
                .eq(YbNote::getEntryId,bo.getEntryId())
                .eq(YbNote::getUserId,getUserId())
                .last("LIMIT 1"));
        if(note!=null){
            note.setContent(bo.getContent());
            if(!iYbNoteService.updateById(note)){
                return AjaxResult.error("笔记编辑失败");
            }
        }else{
            note = new YbNote();
            note.setTeachingMaterialId(teachingMaterialEntry.getTeachingMaterialId());
            note.setEntryId(bo.getEntryId());
            note.setUserId(getUserId());
            note.setContent(bo.getContent());

            if(!iYbNoteService.save(note)){
                return AjaxResult.error("笔记编辑失败");
            }
        }

        return AjaxResult.success("笔记编辑成功");
    }

    @ApiOperation("查询词条笔记")
    @PostMapping("/getNote")
    @LoginChecked
    public AjaxResult editNote(@RequestBody IdBo bo) {

        YbTeachingMaterialEntry teachingMaterialEntry = iYbTeachingMaterialEntryService.getById(bo.getId());
        if(teachingMaterialEntry==null){
            return AjaxResult.error("词条不存在");
        }

        //查询笔记信息
        YbNote note = iYbNoteService.getOne(new LambdaQueryWrapper<YbNote>()
                .eq(YbNote::getEntryId,bo.getId())
                .eq(YbNote::getUserId,getUserId())
                .last("LIMIT 1"));
        return AjaxResult.success(note);
    }

}
