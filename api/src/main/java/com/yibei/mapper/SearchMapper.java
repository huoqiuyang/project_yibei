package com.yibei.mapper;

import com.yibei.yb.domain.dto.ExpandingItem;
import com.yibei.yb.domain.dto.MaterialItem;
import com.yibei.yb.domain.dto.TopicItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SearchMapper {

    List<MaterialItem> searMaterList(@Param("keyword") String keyword);

    List<ExpandingItem> searExpand(@Param("keyword") String keyword);

    List<TopicItemDTO> searTopic(@Param("keyword") String keyword);
}
