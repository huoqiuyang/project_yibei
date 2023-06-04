package com.yibei.yb.mapper;

import com.yibei.yb.domain.clientVo.YbTmeCatalogueSecondInfoVo;
import com.yibei.yb.domain.vo.ListenBookItemVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ListenBookMapper {
    List<ListenBookItemVO> bookList(@Param("userId") Long userId);

    @Select("SELECT DISTINCT teaching_material_id FROM yb_teaching_material_entry WHERE audio_frequency IS NOT NULl AND is_deleted = 0")
    List<Long> searchAudioMater();

    /**
     * 查询播单的二级菜单
     * @param id 一级菜单id
     * @param userId 用户id
     * @return
     */
    List<YbTmeCatalogueSecondInfoVo> searchSecondList(@Param("firstId") Long id, @Param("userId") Long userId);

    /**
     * 批量保存播单
     * @param userId
     * @param materialId
     * @param entryList
     * @return
     */
    int savePlayList(@Param("userId") Long userId,
                     @Param("materialId") Long materialId,
                     @Param("entryList") List<YbTmeCatalogueSecondInfoVo> entryList);

    /**
     * 清除播单
     * @param userId
     * @param materialId
     */
    @Update("UPDATE yb_play_list SET is_deleted = 1 WHERE user_id = #{userId} AND material_id = #{materialId}")
    void clearPlayList(@Param("userId") Long userId, @Param("materialId") Long materialId);

    @Select("SELECT img_url FROM yb_teaching_material_entry WHERE id = #{entryId}")
    String searchPictures(@Param("entryId") Long entryId);
}
