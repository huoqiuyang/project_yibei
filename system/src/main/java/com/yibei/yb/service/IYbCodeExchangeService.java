package com.yibei.yb.service;

import com.yibei.yb.domain.YbCodeExchange;
import com.yibei.yb.domain.vo.YbCodeExchangeVo;
import com.yibei.yb.domain.bo.YbCodeExchangeBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 兑换记录Service接口
 *
 * @author yibei
 * @date 2022-05-26
 */
public interface IYbCodeExchangeService extends IServicePlus<YbCodeExchange, YbCodeExchangeVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbCodeExchangeVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbCodeExchangeVo> queryPageList(YbCodeExchangeBo bo);

	/**
	 * 查询列表
	 */
	List<YbCodeExchangeVo> queryList(YbCodeExchangeBo bo);

	/**
	 * 根据新增业务对象插入兑换记录
	 * @param bo 兑换记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbCodeExchangeBo bo);

	/**
	 * 根据编辑业务对象修改兑换记录
	 * @param bo 兑换记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbCodeExchangeBo bo);

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
	YbCodeExchangeVo queryExtData(YbCodeExchangeVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbCodeExchangeVo> queryExtData(List<YbCodeExchangeVo> list);
}
