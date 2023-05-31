package com.yibei.system.service;


import com.yibei.common.core.page.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.CityRegion;
import com.yibei.system.domain.bo.CityRegionAddBo;
import com.yibei.system.domain.bo.CityRegionEditBo;
import com.yibei.system.domain.bo.CityRegionQueryBo;
import com.yibei.system.domain.vo.CityRegionVo;

import java.util.Collection;
import java.util.List;

/**
* 【请填写功能名称】Service接口
*
* @author frame
* @date 2021-07-14
*/
public interface ICityRegionService extends IServicePlus<CityRegion> {

    /**
    * 查询单个
    * @return
    */
    CityRegionVo queryById(Integer id);

    /**
    * 查询列表
    */
    TableDataInfo<CityRegionVo> queryPageList(CityRegionQueryBo bo);

    /**
    * 查询列表
    */
    List<CityRegionVo> queryList(CityRegionQueryBo bo);

    /**
    * 根据新增业务对象插入【请填写功能名称】
    * @param bo 【请填写功能名称】新增业务对象
    * @return
    */
    Boolean insertByAddBo(CityRegionAddBo bo);

    /**
    * 根据编辑业务对象修改【请填写功能名称】
    * @param bo 【请填写功能名称】编辑业务对象
    * @return
    */
    Boolean updateByEditBo(CityRegionEditBo bo);

    /**
    * 校验并删除数据
    * @param ids 主键集合
    * @param isValid 是否校验,true-删除前校验,false-不校验
    * @return
    */
    Boolean deleteWithValidByIds(Collection<Integer> ids, Boolean isValid);
}
