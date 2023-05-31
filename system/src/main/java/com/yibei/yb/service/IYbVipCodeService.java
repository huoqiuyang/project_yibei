package com.yibei.yb.service;

import com.yibei.yb.domain.YbVipCode;
import com.yibei.yb.domain.vo.YbVipCodeVo;
import com.yibei.yb.domain.bo.YbVipCodeBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 会员兑换码Service接口
 *
 * @author yibei
 * @date 2022-04-27
 */
public interface IYbVipCodeService extends IServicePlus<YbVipCode, YbVipCodeVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbVipCodeVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbVipCodeVo> queryPageList(YbVipCodeBo bo);

	/**
	 * 查询列表
	 */
	List<YbVipCodeVo> queryList(YbVipCodeBo bo);

	/**
	 * 根据新增业务对象插入会员兑换码
	 * @param bo 会员兑换码新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbVipCodeBo bo);

	/**
	 * 根据编辑业务对象修改会员兑换码
	 * @param bo 会员兑换码编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbVipCodeBo bo);

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
	YbVipCodeVo queryExtData(YbVipCodeVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbVipCodeVo> queryExtData(List<YbVipCodeVo> list);
}
