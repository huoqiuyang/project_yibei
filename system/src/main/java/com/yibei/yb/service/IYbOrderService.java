package com.yibei.yb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.yb.domain.YbOrder;
import com.yibei.yb.domain.clientVo.YbOrderInfoVo;
import com.yibei.yb.domain.vo.YbOrderVo;
import com.yibei.yb.domain.bo.YbOrderBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 会员订单Service接口
 *
 * @author yibei
 * @date 2022-04-27
 */
public interface IYbOrderService extends IServicePlus<YbOrder, YbOrderVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbOrderVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbOrderVo> queryPageList(YbOrderBo bo);

	/**
	 * 查询列表
	 */
	List<YbOrderVo> queryList(YbOrderBo bo);

	/**
	 * 根据新增业务对象插入会员订单
	 * @param bo 会员订单新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbOrderBo bo);

	/**
	 * 根据编辑业务对象修改会员订单
	 * @param bo 会员订单编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbOrderBo bo);

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
	YbOrderVo queryExtData(YbOrderVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbOrderVo> queryExtData(List<YbOrderVo> list);

	/**
	 * 用户交易记录列表
	 * */
	Page<YbOrderInfoVo> orderRecordList(PageBo bo,Long userId);
}
