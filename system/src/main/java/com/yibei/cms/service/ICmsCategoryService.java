package com.yibei.cms.service;

import com.yibei.cms.domain.CmsCategory;
import com.yibei.cms.domain.vo.CmsCategoryVo;
import com.yibei.cms.domain.bo.CmsCategoryBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 内容分类Service接口
 *
 * @author yibei
 * @date 2022-01-08
 */
public interface ICmsCategoryService extends IServicePlus<CmsCategory, CmsCategoryVo> {
	/**
	 * 查询单个
	 * @return
	 */
	CmsCategoryVo queryById(Long id);


	/**
	 * 查询列表
	 */
	List<CmsCategoryVo> queryList(CmsCategoryBo bo);

	/**
	 * 根据新增业务对象插入内容分类
	 * @param bo 内容分类新增业务对象
	 * @return
	 */
	Boolean insertByBo(CmsCategoryBo bo);

	/**
	 * 根据编辑业务对象修改内容分类
	 * @param bo 内容分类编辑业务对象
	 * @return
	 */
	Boolean updateByBo(CmsCategoryBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	/**
	 * 查询关联数据
	 * @return
	 */
	CmsCategoryVo queryExtData(CmsCategoryVo vo);

	/**
	 * 查询关联数据
	 * @return
	 */
	List<CmsCategoryVo> queryExtData(List<CmsCategoryVo> list);
}
