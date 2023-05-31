package com.yibei.yb.mapper;

import com.yibei.yb.domain.YbStudyTimeLog;
import com.yibei.common.core.mybatisplus.core.BaseMapperPlus;
import com.yibei.yb.domain.clientVo.TranscendVo;
import org.apache.ibatis.annotations.Param;

/**
 * 学习时间记录Mapper接口
 *
 * @author yibei
 * @date 2022-05-18
 */
public interface YbStudyTimeLogMapper extends BaseMapperPlus<YbStudyTimeLog> {

    /**
     * 获取用户学习时间
     * */
    Integer getUserStudyMin(@Param("userId")Long userId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 获取用户当日学习时长超越人数相关信息
     * */
    TranscendVo getTranscendInfo(@Param("userId")Long userId, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("userStudyMin")int userStudyMin);

}
