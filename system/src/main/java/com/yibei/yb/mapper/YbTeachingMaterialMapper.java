package com.yibei.yb.mapper;

import com.yibei.yb.domain.YbTeachingMaterial;
import com.yibei.common.core.mybatisplus.core.BaseMapperPlus;
import com.yibei.yb.domain.clientVo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教材Mapper接口
 *
 * @author yibei
 * @date 2022-05-11
 */
public interface YbTeachingMaterialMapper extends BaseMapperPlus<YbTeachingMaterial> {

    /**
     * 学习报告-阅读进度
     * */
    List<ReportStudyReadVo> getReportReadList(Long userId);

    /**
     * 学习报告-阅读进度
     * */
    List<ReportStudyReciteVo> getReportReciteList(Long userId);

    /**
     * 学习报告-模拟测试
     * */
    List<ReportStudyQuestionBankVo> getReportQuestionBankList(Long userId);

    /**
     * 学习报告-拓展阅读
     * */
    List<ReportStudyExpandReadingVo> getReportExpandReadingList(Long userId);

    /**
     * 学习报告-近期学习记录
     * */
    List<ReportStudyLogInfoVo> getReportStudyLogList(@Param("userId")Long userId, @Param("size")int size);

}
