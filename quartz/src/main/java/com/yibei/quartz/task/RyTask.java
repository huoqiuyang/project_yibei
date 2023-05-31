//package com.yibei.quartz.task;
//
//import cn.hutool.core.lang.Console;
//import com.yibei.common.utils.StringUtils;
//import com.yibei.system.domain.User;
//import com.yibei.system.service.IUserService;
//import com.yibei.yb.domain.UserIntegralRecord;
//import com.yibei.yb.service.IUserIntegralRecordService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.time.LocalDate;
//import java.util.List;
//
///**
// * 定时任务调度测试
// *
// * @author
// */
//@Slf4j
//@Component("ryTask")
//public class RyTask
//{
//    @Autowired
//    private IUserIntegralRecordService iUserIntegralRecordService;
//    @Autowired
//    private IUserService iUserService;
//
//    public void integralExpired(){
//        log.info("执行积分过期处理");
//        if(LocalDate.now().getMonthValue()==1 && LocalDate.now().getDayOfMonth() == 1){
//            List<User> userList = iUserService.list();
//            UserIntegralRecord integral = new UserIntegralRecord();
//            for (User user : userList) {
//                if(user.getIntegral() > 0){
//
//                    //添加积分记录
//                    integral = new UserIntegralRecord();
//                    integral.setUserId(user.getId());
//                    integral.setStatement("已过期积分");
//                    integral.setIncome(user.getIntegral());
//                    integral.setDiscrepant("-");
//                    iUserIntegralRecordService.save(integral);
//
//                    user.setIntegral(0);
//                    iUserService.updateById(user);
//                }
//            }
//        }
//    }
//}
