package com.yibei.yb.mapper;

import com.yibei.yb.domain.YbSignin;
import com.yibei.common.core.mybatisplus.core.BaseMapperPlus;
import com.yibei.yb.domain.clientVo.ReportSigninInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 签到Mapper接口
 *
 * @author yibei
 * @date 2022-05-18
 */
public interface YbSigninMapper extends BaseMapperPlus<YbSignin> {

    /**
     * 学习报告-查询打卡信息
     * @return
     */
    List<ReportSigninInfoVo> getReportSignin(@Param("userId")Long userId, @Param("monthDay")String monthDay);

}
