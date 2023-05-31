package com.yibei.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.system.domain.User;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.domain.bo.UserBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.yb.domain.YbUserVip;

import java.util.Collection;
import java.util.List;

/**
 * 用户Service接口
 *
 * @author yibei
 * @date 2022-04-26
 */
public interface IUserService extends IServicePlus<User, UserVo> {
	/**
	 * 查询单个
	 * @return
	 */
	UserVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<UserVo> queryPageList(UserBo bo);

	/**
	 * 查询列表
	 */
	List<UserVo> queryList(UserBo bo);

	/**
	 * 根据新增业务对象插入用户
	 * @param bo 用户新增业务对象
	 * @return
	 */
	Boolean insertByBo(UserBo bo);

	/**
	 * 根据编辑业务对象修改用户
	 * @param bo 用户编辑业务对象
	 * @return
	 */
	Boolean updateByBo(UserBo bo);

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
	UserVo queryExtData(UserVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<UserVo> queryExtData(List<UserVo> list);

	/**
	 * 获取用户信息列表
	 * */
	Page<UserVo> getList(UserBo bo);

	/**
	 * 获取用户会员信息
	 * */
	YbUserVip getUserVipInfo(Long userId);
}
