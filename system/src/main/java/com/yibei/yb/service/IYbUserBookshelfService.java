package com.yibei.yb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.yb.domain.YbUserBookshelf;
import com.yibei.yb.domain.clientVo.YbUserBookshelfInfoVo;
import com.yibei.yb.domain.vo.YbUserBookshelfVo;
import com.yibei.yb.domain.bo.YbUserBookshelfBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 用户书架Service接口
 *
 * @author yibei
 * @date 2022-05-06
 */
public interface IYbUserBookshelfService extends IServicePlus<YbUserBookshelf, YbUserBookshelfVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbUserBookshelfVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbUserBookshelfVo> queryPageList(YbUserBookshelfBo bo);

	/**
	 * 查询列表
	 */
	List<YbUserBookshelfVo> queryList(YbUserBookshelfBo bo);

	/**
	 * 根据新增业务对象插入用户书架
	 * @param bo 用户书架新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbUserBookshelfBo bo);

	/**
	 * 根据编辑业务对象修改用户书架
	 * @param bo 用户书架编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbUserBookshelfBo bo);

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
	YbUserBookshelfVo queryExtData(YbUserBookshelfVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbUserBookshelfVo> queryExtData(List<YbUserBookshelfVo> list);


	/**
	 * 根据用户查询是否加入书架
	 * @param contentType 类型 1教材2题库
	 * @param contentId 内容id
	 * @param userId 用户id
	 * @return
	 */
	YbUserBookshelf getUserBookshelf(Integer contentType,Long contentId,Long userId);

	/**
	 * 每一次查看详情时都刷新 用户在该教材/题库的 [最近打开时间]
	 * */
	void updateLastTime(Long userId,Long contentType,Long contentId);

	/**
	 * 书架列表信息
	 * */
	Page<YbUserBookshelfInfoVo> getListInfo(PageBo bo, Long userId);

	/**
	 * 是否加入书架
	 * userId:用户ID，contentType类型：教材1题库2，contentId：教材题库ID
	 * */
	long getIsBookshelf(Long userId,Long contentType,Long contentId);

}
