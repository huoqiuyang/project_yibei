package com.yibei.system.service;



import com.yibei.common.core.page.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.PageData;
import com.yibei.system.domain.CityInfo;
import com.yibei.system.domain.bo.CityInfoAddBo;
import com.yibei.system.domain.bo.CityInfoEditBo;
import com.yibei.system.domain.bo.CityInfoQueryBo;
import com.yibei.system.domain.bo.ParentIdBo;
import com.yibei.system.domain.vo.AMapCityInfoVo;
import com.yibei.system.domain.vo.CityInfoVo;

import java.util.Collection;
import java.util.List;

/**
* 【请填写功能名称】Service接口
*
* @author frame
* @date 2021-07-14
*/
public interface ICityInfoService extends IServicePlus<CityInfo> {

    /**
    * 查询单个
    * @return
    */
    CityInfoVo queryById(Integer id);

    /**
    * 查询列表
    */
    TableDataInfo<CityInfoVo> queryPageList(CityInfoQueryBo bo);

    /**
    * 查询列表
    */
    List<CityInfoVo> queryList(CityInfoQueryBo bo);

    /**
    * 根据新增业务对象插入【请填写功能名称】
    * @param bo 【请填写功能名称】新增业务对象
    * @return
    */
    Boolean insertByAddBo(CityInfoAddBo bo);

    /**
    * 根据编辑业务对象修改【请填写功能名称】
    * @param bo 【请填写功能名称】编辑业务对象
    * @return
    */
    Boolean updateByEditBo(CityInfoEditBo bo);

    /**
    * 校验并删除数据
    * @param ids 主键集合
    * @param isValid 是否校验,true-删除前校验,false-不校验
    * @return
    */
    Boolean deleteWithValidByIds(Collection<Integer> ids, Boolean isValid);

    List<PageData> getMultilevelSortList(ParentIdBo vo);

    List<PageData> getMultilevelSortStreetList(ParentIdBo vo);

    List<PageData> cityUrbanList();

    AMapCityInfoVo getCityInfoByLocation(String lon, String lat);
}
