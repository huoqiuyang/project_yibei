package com.yibei.client.controller.yb;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.CalculationUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.common.utils.TimeUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.system.domain.SysConfig;
import com.yibei.system.service.ISysConfigService;
import com.yibei.yb.domain.*;
import com.yibei.yb.domain.clientBo.ReportSigninInfoBo;
import com.yibei.yb.domain.clientBo.TimeRecordInfoBo;
import com.yibei.yb.domain.clientVo.*;
import com.yibei.yb.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController("Client_StudyStatisticsController")
@Api(value = "学习统计模块",tags = "学习统计模块")
@RequestMapping("/client/studyStatistics")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class StudyStatisticsController extends BaseController {

    private final IYbStudyTimeLogService iYbStudyTimeLogService;

    private final IYbTeachingMaterialService iYbTeachingMaterialService;

    private final IYbQuestionBankService iYbQuestionBankService;

    private final IYbExpandReadingService iYbExpandReadingService;

    private final IYbSigninService iYbSigninService;

    private final ISysConfigService iSysConfigService;

    private final IYbStudyConfigService iYbStudyConfigService;

    @ApiOperation("增加学习时间记录")
    @PostMapping("/addTimeRecord")
    @LoginChecked
    public AjaxResult addTimeRecord(@RequestBody TimeRecordInfoBo bo) {

        Integer studyType = bo.getStudyType();//学习类型（1阅读2背诵3做题4拓展阅读）

        //验证内容是否存在
        if(studyType==1 || studyType==2){
            YbTeachingMaterial teachingMaterial = iYbTeachingMaterialService.getById(bo.getId());
            if(teachingMaterial == null){
                return AjaxResult.error("教材不存在");
            }
        }else if(studyType==3){
            YbQuestionBank questionBank = iYbQuestionBankService.getById(bo.getId());
            if(questionBank == null){
                return AjaxResult.error("题库不存在");
            }
        }else if(studyType==4){
            YbExpandReading expandReading = iYbExpandReadingService.getById(bo.getId());
            if(expandReading == null){
                return AjaxResult.error("扩展阅读不存在");
            }
        }else{
            return AjaxResult.error("学习类型错误");
        }

        //时间记录统计
        String nowDay = TimeUtils.getCurrentDate("yyyy-MM-dd");
        YbStudyTimeLog studyTimeLog = iYbStudyTimeLogService.getOne(new LambdaQueryWrapper<YbStudyTimeLog>()
                .eq(YbStudyTimeLog::getUserId,getUserId())
                .eq(YbStudyTimeLog::getType,studyType)
                .eq(YbStudyTimeLog::getContentId,bo.getId())
                .eq(YbStudyTimeLog::getDateDay,nowDay)
                .last("LIMIT 1"));
        if(studyTimeLog != null){
            //修改记录
            studyTimeLog.setMin(studyTimeLog.getMin()+bo.getMin());
            if(!iYbStudyTimeLogService.updateById(studyTimeLog)){
                return AjaxResult.error("记录失败");
            }
        }else{
            //创建记录
            studyTimeLog = new YbStudyTimeLog();
            studyTimeLog.setType(studyType);
            studyTimeLog.setUserId(getUserId());
            studyTimeLog.setContentId(bo.getId());
            studyTimeLog.setDateDay(nowDay);
            studyTimeLog.setMin(bo.getMin());
            if(!iYbStudyTimeLogService.save(studyTimeLog)){
                return AjaxResult.error("记录失败");
            }
        }

        return AjaxResult.success("记录成功");
    }

    @ApiOperation("获取用户打卡学习信息")
    @PostMapping("/getSignStudyTimeInfo")
    @LoginChecked
    public AjaxResult<SignStudyTimeInfoVo> getSignStudyTimeInfo() {

        SignStudyTimeInfoVo vo = new SignStudyTimeInfoVo();
        //用户信息
        vo.setUser(getUserByLogin());

        //当天日期
        String tDay = TimeUtils.getCurrentDate("yyyy-MM-dd");
        //前一天日期
        String yDay = TimeUtils.formatIntToDateString(TimeUtils.getCurrentTime()-86400l,"yyyy-MM-dd");
        //当天开始时间
        String startTime = TimeUtils.getCurrentDate("yyyy-MM-dd 00:00:00");
        //当前时间
        String nowTime = TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss");

        //打卡天数
        List<YbSignin> signinList = iYbSigninService.list(new LambdaQueryWrapper<YbSignin>()
                .eq(YbSignin::getUserId,getUserId())
                .orderByDesc(YbSignin::getCreateTime));
        if(signinList!=null && signinList.size()>0){
            //坚持打卡天数
            vo.setInsistSignDay(signinList.size());
            //连续打卡天数
            YbSignin ybSignin = signinList.get(0);
            if(tDay.equals(ybSignin.getDateDay()) || yDay.equals(ybSignin.getDateDay())){
                vo.setContinuitySignDay(ybSignin.getContinuityDay().intValue());
            }
        }

        //当日学习时间（分钟）
        Integer min = iYbStudyTimeLogService.getUserStudyMin(getUserId(),startTime,nowTime);
        if(min!=null && min>0){
            vo.setToDayStudyMin(min);
        }

        //超越用户百分比
        if(vo.getToDayStudyMin()>0){
            TranscendVo transcendVo = iYbStudyTimeLogService.getTranscendInfo(getUserId(),startTime,nowTime,vo.getToDayStudyMin());
            String transcendPercentage = CalculationUtils.divisionFormat(transcendVo.getTranscend(),transcendVo.getCounts(),"0.0%");
            if(transcendPercentage.equals("100.0%")){
                transcendPercentage = "99.9%";
            }
            vo.setTranscendPercentage(transcendPercentage.replaceAll("%",""));
        }else{
            vo.setTranscendPercentage("0");
        }

        return AjaxResult.success(vo);
    }

    @ApiOperation("学习报告-打卡信息")
    @PostMapping("/getReportSignin")
    @LoginChecked
    public AjaxResult<List<ReportSigninInfoVo>> getReportSignin(@RequestBody ReportSigninInfoBo bo) {

        List<ReportSigninInfoVo> vo = iYbSigninService.getReportSignin(getUserId(),bo.getMonth());

        return AjaxResult.success(vo);
    }

    @ApiOperation("学习报告-学习时间")
    @PostMapping("/getReportStudyTime")
    @LoginChecked
    public AjaxResult<ReportStudyTimeInfoVo> getReportStudyTime() {

        //当前时间
        String nowTime = TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss");
        //本周开始时间
        String weekStartTime = TimeUtils.weekStartTime("yyyy-MM-dd 00:00:00");
        //本月开始时间
        String monthStartTime = TimeUtils.getMonthFirstDay()+" 00:00:00";

        ReportStudyTimeInfoVo vo = new ReportStudyTimeInfoVo();
        vo.setTotalStudyMin(iYbStudyTimeLogService.getUserStudyMin(getUserId(),null,null));
        vo.setMonthStudyMin(iYbStudyTimeLogService.getUserStudyMin(getUserId(),monthStartTime,nowTime));
        vo.setWeekStudyMin(iYbStudyTimeLogService.getUserStudyMin(getUserId(),weekStartTime,nowTime));

        return AjaxResult.success(vo);
    }

    @ApiOperation("学习报告-学习进度")
    @PostMapping("/getReportStudyProgressed")
    @LoginChecked
    public AjaxResult<ReportStudyInfoVo> getReportStudyProgressed() {

        ReportStudyInfoVo vo = new ReportStudyInfoVo();

        //数据依据：书架
        //1、阅读进度
        List<ReportStudyReadVo> readListVo = iYbTeachingMaterialService.getReportReadList(getUserId());
        for(ReportStudyReadVo readVo : readListVo){
            //已学百分比
            readVo.setProportion(CalculationUtils.divisionFormat(readVo.getIsRead(),readVo.getCounts(),"0.0%"));
            //是否显示预计剩余时长（0不显示1显示）
            if(readVo.getIsRead()>0 && readVo.getCounts()>0 && readVo.getCounts()-readVo.getIsRead()>0 && readVo.getMin()>=5){
                readVo.setIsDisplay(1);
                //预计还需小时数
                readVo.setHourNum(getHour(readVo.getCounts(),readVo.getIsRead(),readVo.getMin()));
            }else{
                readVo.setIsDisplay(0);
            }
        }
        vo.setReadListVo(readListVo);

        //2、背诵进度
        double reciteNumConfig = Double.valueOf(iSysConfigService.getOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getConfigKey,"study_entry_num").last("LIMIT 1")).getConfigValue());
        List<ReportStudyReciteVo> reciteListVo = iYbTeachingMaterialService.getReportReciteList(getUserId());
        for(ReportStudyReciteVo reciteVo : reciteListVo){
            //是否显示预计剩余时长（0不显示1显示）
            if(reciteVo.getCounts()-reciteVo.getIsRecite()>0 && reciteVo.getCounts()>0 && reciteVo.getIsRecite()>0 && reciteVo.getMin()>=5){
                reciteVo.setIsDisplay(1);
                //每天背诵个数
                double reciteNum = reciteNumConfig;
                //预计还需天数
                YbStudyConfig studyConfig = iYbStudyConfigService.getOne(new LambdaQueryWrapper<YbStudyConfig>()
                        .eq(YbStudyConfig::getTeachingMaterialId,reciteVo.getId())
                        .eq(YbStudyConfig::getUserId,getUserId())
                        .last("LIMIT 1"));
                if(studyConfig!=null){
                    reciteNum = studyConfig.getQuantity();
                }

                //避免每天学习量设置为0
                if(reciteNum!=0){
                    reciteVo.setDayNum(getReciteDay(reciteVo.getCounts(),reciteVo.getIsRecite(),reciteNum));
                }else{
                    reciteVo.setIsDisplay(0);
                }
            }else{
                reciteVo.setIsDisplay(0);
            }
        }
        vo.setReciteListVo(reciteListVo);

        //3、模拟测试
        List<ReportStudyQuestionBankVo> questionBankListVo = iYbTeachingMaterialService.getReportQuestionBankList(getUserId());
        for(ReportStudyQuestionBankVo questionBankVo : questionBankListVo) {
            //是否显示预计剩余时长（0不显示1显示）
            if (questionBankVo.getIsDone() > 0 && questionBankVo.getCounts() > 0 && questionBankVo.getCounts() - questionBankVo.getIsDone() > 0 && questionBankVo.getMin() >= 5) {
                questionBankVo.setIsDisplay(1);
                //预计还需小时数
                questionBankVo.setHourNum(getHour(questionBankVo.getCounts(), questionBankVo.getIsDone(), questionBankVo.getMin()));
            } else {
                questionBankVo.setIsDisplay(0);
            }
        }
        vo.setQuestionBankListVo(questionBankListVo);

        //4、拓展阅读
        int expandReadingNum = 0;
        List<ReportStudyExpandReadingVo> expandReadingListVo = iYbTeachingMaterialService.getReportExpandReadingList(getUserId());
        if(expandReadingListVo!=null && expandReadingListVo.size()>0){
            expandReadingNum = expandReadingListVo.size();
        }
        vo.setExpandReadingListVo(expandReadingListVo);
        vo.setExpandReadingNum(expandReadingNum);

        return AjaxResult.success(vo);
    }

    @ApiOperation("学习报告-近期学习记录")
    @PostMapping("/getReportStudyLog")
    @LoginChecked
    public AjaxResult<List<ReportStudyLogInfoVo>> getReportStudyLog() {

        //近期学习默认显示10条
        int size = 10;

        List<ReportStudyLogInfoVo> listVo = iYbTeachingMaterialService.getReportStudyLogList(getUserId(),size);
        listVo.forEach(vo->{
            //数据处理
            String[] infoArr = vo.getInfo().split(",");
            for(String info : infoArr){
                if(info.indexOf("_")>-1){
                    String type = info.substring(0,info.indexOf("_"));
                    String numStr = info.substring(info.indexOf("_")+1);
                    if(StringUtils.isNotBlank(type) && StringUtils.isNotBlank(numStr)){
                        int num = Integer.parseInt(numStr);
                        if(type.equals("1")){
                            //阅读
                            vo.setReadNum(num);
                        }else if(type.equals("2")){
                            //背诵
                            vo.setReciteNum(num);
                        }else if(type.equals("3")){
                            //做题
                            vo.setQuestionBankNum(num);
                        }else if(type.equals("4")){
                            //拓展阅读
                            vo.setExpandReadingNum(num);
                        }
                    }
                }
            }
        });

        return AjaxResult.success(listVo);
    }

    /**
     * 阅读进度>预计还需小时数hour = ((阅读分钟数/已学词条数)*(词条总数-已学词条数))/60  -- 向上取整小时数
     * 模拟测试>预计还需小时数hour = ((做题分钟数/已做题目数)*(题目总数-已做题目数))/60  -- 向上取整小时数
     * */
    private int getHour(double counts,double isRead,double min){
        int h = 0;
        try {
            double m = (min/isRead)*(counts-isRead);
            h = new Double(Math.ceil(m/60)).intValue();
        }catch (Exception e){
            h = 0;
        }
        return h;
    }

    /**
     * 背诵进度-预计还需天数
     * day = (背诵计划词条总数-已背词条数量)/每天学习数量  -- 向上取整天数
     * */
    private int getReciteDay(double counts,double isRecite,double reciteNum){
        int d = 0;
        try {
            double day = Math.ceil((counts-isRecite)/reciteNum);
            d = new Double(day).intValue();
        }catch (Exception e){
            d = 0;
        }
        return d;
    }

}
