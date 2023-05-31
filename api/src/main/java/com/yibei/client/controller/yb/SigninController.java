package com.yibei.client.controller.yb;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.TimeUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.yb.domain.YbSignin;
import com.yibei.yb.service.IYbSigninService;
import com.yibei.yb.service.IYbStudyTimeLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Client_SigninController")
@Api(value = "签到模块",tags = "签到模块")
@RequestMapping("/client/signin")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SigninController extends BaseController {

    private final IYbSigninService iYbSigninService;

    private final IYbStudyTimeLogService iYbStudyTimeLogService;

    @ApiOperation("签到打卡")
    @PostMapping("/sign")
    @LoginChecked
    public AjaxResult sign() {

        //当前时间
        String nowTime = TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss");
        //当天日期
        String tDay = TimeUtils.getCurrentDate("yyyy-MM-dd");
        //前一天日期
        String yDay = TimeUtils.formatIntToDateString(TimeUtils.getCurrentTime()-86400l,"yyyy-MM-dd");
        //前两天日期
        String yTDay = TimeUtils.formatIntToDateString(TimeUtils.getCurrentTime()-86400l*2,"yyyy-MM-dd");

        //当天签到开始时间
        String tDayStartTime = TimeUtils.getCurrentDate("yyyy-MM-dd 04:00:00");
        //当天签到结束时间
        String tDayEndTime = TimeUtils.formatIntToDateString(TimeUtils.getCurrentTime()+86400l,"yyyy-MM-dd 03:59:59");
        //前一天签到开始时间
        String yDayStartTime = TimeUtils.formatIntToDateString(TimeUtils.getCurrentTime()-86400l,"yyyy-MM-dd 04:00:00");
        //前一天签到结束时间
        String yDayEndTime = TimeUtils.getCurrentDate("yyyy-MM-dd 03:59:59");

        //当前时间是否归属于昨日签到
        boolean flg = TimeUtils.timeCompare(nowTime,tDayStartTime,"yyyy-MM-dd HH:mm:ss");

        //学习时间超过5分钟才能打卡
        Integer min;
//        if(flg){
//            min = iYbStudyTimeLogService.getUserStudyMin(getUserId(),yDayStartTime,yDayEndTime);
//        }else{
//            min = iYbStudyTimeLogService.getUserStudyMin(getUserId(),tDayStartTime,tDayEndTime);
//        }
        min = iYbStudyTimeLogService.getUserStudyMin(getUserId(),TimeUtils.getCurrentDate("yyyy-MM-dd 00:00:00"),TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
        if(min==null || min<=5){
            return AjaxResult.error("学习时间还没有超过5分钟,无法打卡哦~");
        }

        //当天签到类型 1:今日签到，2：昨日签到
        int type = 1;
        //较打卡日的前一日的信息
        YbSignin lastSignin = null;
        //今日打卡信息
        YbSignin signin = null;

        if(flg){
            //前一天签到
            signin = iYbSigninService.getOne(new LambdaQueryWrapper<YbSignin>()
                    .eq(YbSignin::getUserId,getUserId())
                    .eq(YbSignin::getDateDay,yDay)
                    .last("LIMIT 1"));
            lastSignin = iYbSigninService.getOne(new LambdaQueryWrapper<YbSignin>()
                    .eq(YbSignin::getUserId,getUserId())
                    .eq(YbSignin::getDateDay,yTDay)
                    .last("LIMIT 1"));
            type = 2;
        }else{
            //当天签到
            signin = iYbSigninService.getOne(new LambdaQueryWrapper<YbSignin>()
                    .eq(YbSignin::getUserId,getUserId())
                    .eq(YbSignin::getDateDay,tDay)
                    .last("LIMIT 1"));
            lastSignin = iYbSigninService.getOne(new LambdaQueryWrapper<YbSignin>()
                    .eq(YbSignin::getUserId,getUserId())
                    .eq(YbSignin::getDateDay,yDay)
                    .last("LIMIT 1"));
        }

        if(signin!=null){
            return AjaxResult.success("今日已打卡");
        }

        //签到打卡
        YbSignin newSignin = new YbSignin();
        newSignin.setUserId(getUserId());
        if(type == 1){
            newSignin.setDateDay(tDay);
        }else{
            newSignin.setDateDay(yDay);
        }
        if(lastSignin!=null){
            newSignin.setContinuityDay(lastSignin.getContinuityDay()+1l);
        }else{
            newSignin.setContinuityDay(1l);
        }

        if(!iYbSigninService.save(newSignin)){
            return AjaxResult.error("打卡失败");
        }

        return AjaxResult.success("打卡成功");
    }

}
