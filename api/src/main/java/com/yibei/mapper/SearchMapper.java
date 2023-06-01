package com.yibei.mapper;

import com.yibei.yb.domain.dto.ExpandingItem;
import com.yibei.yb.domain.dto.MaterialItem;
import com.yibei.yb.domain.dto.TopicItemDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 　 功能描述
 *
 * 　 <p>
 * 　 -----------------------------------------------------------------------------
 * 　 <p>
 * 　 工程名 ： yibei
 * 　 <p>
 * 　 授权 : (C) Copyright topwalk Corporation 2014-2023
 * 　 <p>
 * 　 公司 : 托尔思天行网安信息技术有限责任公司
 * 　 <p>
 * 　 ------------------------------------------------------------------- ----------
 * 　 <p>
 * 　 <font color="#FF0000">注意: 本内容仅限于拓尔思天行网安公司内部使用，禁止转发</font>
 * 　 <p>
 *
 * 　 @version 1.0
 * 　 @author huoqy
 * 　 @createDate 2023年06月01日 20:18
 * 　 @since JDK1.8
 */
@Mapper
public interface SearchMapper {

    @Select("")
    List<MaterialItem> searMaterList(String keyword);

    List<ExpandingItem> searExpand(String keyword);

    List<TopicItemDTO> searTopic(String keyword);
}
