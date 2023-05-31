package com.yibei.yb.service;

import com.yibei.yb.domain.YbUserCollection;
import com.yibei.yb.domain.vo.YbUserCollectionVo;
import com.yibei.yb.domain.bo.YbUserCollectionBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 用户收藏Service接口
 *
 * @author yibei
 * @date 2022-05-06
 */
public interface IYbUserCollectionService extends IServicePlus<YbUserCollection, YbUserCollectionVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbUserCollectionVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbUserCollectionVo> queryPageList(YbUserCollectionBo bo);

	/**
	 * 查询列表
	 */
	List<YbUserCollectionVo> queryList(YbUserCollectionBo bo);

	/**
	 * 根据新增业务对象插入用户收藏
	 * @param bo 用户收藏新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbUserCollectionBo bo);

	/**
	 * 根据编辑业务对象修改用户收藏
	 * @param bo 用户收藏编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbUserCollectionBo bo);

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
	YbUserCollectionVo queryExtData(YbUserCollectionVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbUserCollectionVo> queryExtData(List<YbUserCollectionVo> list);
}
