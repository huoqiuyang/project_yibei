package com.yibei.yb.mapper;

import com.yibei.yb.domain.YbTeachingMaterialEntry;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EntryMapper {

    /**
     * 批量查询id是否存在
     * @param ids
     * @return
     */
    List<YbTeachingMaterialEntry> searchIdByIds(@Param("ids") List<Long> ids);

    /**
     * 查询已经加入的词条
     * @param userId
     * @return
     */
    @Select("SELECT entry_id FROM yb_user_collection WHERE user_id = #{userId} AND content_type = #{contentType} AND is_deleted = 0")
    List<Long> getAddListByUserId(@Param("userId") Long userId, @Param("contentType") Integer contentType);

    /**
     * 保存背诵词条
     * @param preAddList 词条id
     * @param teachingMaterialId 教材id
     * @param userId 用户id
     */
    int saveRecite(@Param("preAddList") List<Long> preAddList, @Param("teachingMaterialId") Long teachingMaterialId, @Param("userId") Long userId);

    /**
     * 批量移除背诵
     * @param readyToMoveList
     * @return
     */
    int remove(@Param("readyToMoveList") List<Long> readyToMoveList);
}
