package com.yibei.yb.service;

import com.yibei.yb.domain.YbOrderPay;
import com.yibei.yb.domain.vo.YbOrderPayVo;
import com.yibei.yb.domain.bo.YbOrderPayBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 支付记录Service接口
 *
 * @author yibei
 * @date 2022-04-27
 */
public interface IYbOrderPayService extends IServicePlus<YbOrderPay, YbOrderPayVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbOrderPayVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbOrderPayVo> queryPageList(YbOrderPayBo bo);

	/**
	 * 查询列表
	 */
	List<YbOrderPayVo> queryList(YbOrderPayBo bo);

	/**
	 * 根据新增业务对象插入支付记录
	 * @param bo 支付记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbOrderPayBo bo);

	/**
	 * 根据编辑业务对象修改支付记录
	 * @param bo 支付记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbOrderPayBo bo);

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
	YbOrderPayVo queryExtData(YbOrderPayVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbOrderPayVo> queryExtData(List<YbOrderPayVo> list);
}
