package com.yibei.system.mapper;

import com.yibei.common.core.page.BaseMapperPlus;
import com.yibei.common.utils.PageData;
import com.yibei.system.domain.CityInfo;
import com.yibei.system.domain.bo.ParentIdBo;

import java.util.List;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author frame
 * @date 2021-07-14
 */
public interface CityInfoMapper extends BaseMapperPlus<CityInfo> {

    List<PageData> getMultilevelSortList(ParentIdBo vo);
    
    List<PageData> getMultilevelSortStreetList(ParentIdBo vo);

    List<PageData> cityUrbanList();
}
