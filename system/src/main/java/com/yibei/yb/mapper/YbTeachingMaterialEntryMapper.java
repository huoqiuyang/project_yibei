package com.yibei.yb.mapper;

import com.yibei.yb.domain.YbTeachingMaterialEntry;
import com.yibei.common.core.mybatisplus.core.BaseMapperPlus;
import com.yibei.yb.domain.clientVo.YbTeachingMaterialEntryReadInfoVo;
import com.yibei.yb.domain.clientVo.YbTeachingMaterialEntryReciteInfoVo;
import com.yibei.yb.domain.clientVo.YbTmeCatalogueInfoVo;
import com.yibei.yb.domain.clientVo.YbTmeCatalogueSecondInfoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教材词条Mapper接口
 *
 * @author yibei
 * @date 2022-05-11
 */
public interface YbTeachingMaterialEntryMapper extends BaseMapperPlus<YbTeachingMaterialEntry> {

    /**
     * 目录标题
     * */
    List<YbTmeCatalogueInfoVo> getCollectionList(@Param("userId")Long userId, @Param("teachingMaterialId")Long teachingMaterialId);

    /**
     * 二级目录信息
     *
     * */
    List<YbTmeCatalogueSecondInfoVo> getSecondList(@Param("userId")Long userId, @Param("teachingMaterialEntryId")Long teachingMaterialEntryId, @Param("isCollection")Integer isCollection);

    /**
     * 最近学习记录查询
     * @param teachingMaterialId 教材id
     * @param userId 用户id
     * @param learningType 学习类型 1阅读2背诵
     * */
    YbTeachingMaterialEntry getEntryLog(@Param("teachingMaterialId")Long teachingMaterialId,@Param("userId")Long userId,@Param("learningType")int learningType);

    /**
     * 词条详情(阅读模式)
     * @param teachingMaterialId 教材id
     * @param selectId 词条id
     * */
    YbTeachingMaterialEntryReadInfoVo getEntryReadInfo(@Param("teachingMaterialId")Long teachingMaterialId, @Param("selectId")Long selectId);

    /**
     * 已读词条数
     * @param teachingMaterialId 教材id
     * @param userId 用户id
     * */
    long getProportionNum(@Param("teachingMaterialId")Long teachingMaterialId, @Param("userId")Long userId);

    /**
     * 我的背诵词条数
     * @param teachingMaterialId 教材id
     * @param userId 用户id
     * */
    long getIsReciteNum(@Param("teachingMaterialId")Long teachingMaterialId, @Param("userId")Long userId);

    /**
     * 我的背诵词条数
     * @param userId 用户id
     * @param teachingMaterialId 教材id
     * @param studyLimit 学习数量
     * @param reviewLimit 复习数量
     * */
    List<YbTeachingMaterialEntryReciteInfoVo> getToDayRecitePlanList(@Param("userId")Long userId, @Param("teachingMaterialId")Long teachingMaterialId, @Param("studyLimit")Integer studyLimit, @Param("reviewLimit")Integer reviewLimit);

    int getReciteEntryList(@Param("userId")Long userId,@Param("teachingMaterialId")Long teachingMaterialId);
}
