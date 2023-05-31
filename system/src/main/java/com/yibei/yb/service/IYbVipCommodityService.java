package com.yibei.yb.service;

import com.yibei.yb.domain.YbVipCommodity;
import com.yibei.yb.domain.vo.YbVipCommodityVo;
import com.yibei.yb.domain.bo.YbVipCommodityBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 会员中心Service接口
 *
 * @author yibei
 * @date 2022-04-27
 */
public interface IYbVipCommodityService extends IServicePlus<YbVipCommodity, YbVipCommodityVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbVipCommodityVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbVipCommodityVo> queryPageList(YbVipCommodityBo bo);

	/**
	 * 查询列表
	 */
	List<YbVipCommodityVo> queryList(YbVipCommodityBo bo);

	/**
	 * 根据新增业务对象插入会员中心
	 * @param bo 会员中心新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbVipCommodityBo bo);

	/**
	 * 根据编辑业务对象修改会员中心
	 * @param bo 会员中心编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbVipCommodityBo bo);

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
	YbVipCommodityVo queryExtData(YbVipCommodityVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbVipCommodityVo> queryExtData(List<YbVipCommodityVo> list);
}
