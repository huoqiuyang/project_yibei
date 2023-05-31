package com.yibei.yb.service;

import com.yibei.yb.domain.YbUserVip;
import com.yibei.yb.domain.vo.YbUserVipVo;
import com.yibei.yb.domain.bo.YbUserVipBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 用户会员Service接口
 *
 * @author yibei
 * @date 2022-04-27
 */
public interface IYbUserVipService extends IServicePlus<YbUserVip, YbUserVipVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbUserVipVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbUserVipVo> queryPageList(YbUserVipBo bo);

	/**
	 * 查询列表
	 */
	List<YbUserVipVo> queryList(YbUserVipBo bo);

	/**
	 * 根据新增业务对象插入用户会员
	 * @param bo 用户会员新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbUserVipBo bo);

	/**
	 * 根据编辑业务对象修改用户会员
	 * @param bo 用户会员编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbUserVipBo bo);

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
	YbUserVipVo queryExtData(YbUserVipVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbUserVipVo> queryExtData(List<YbUserVipVo> list);
}
