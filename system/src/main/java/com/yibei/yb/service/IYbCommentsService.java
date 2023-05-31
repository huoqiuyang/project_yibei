package com.yibei.yb.service;

import com.yibei.yb.domain.YbComments;
import com.yibei.yb.domain.vo.YbCommentsVo;
import com.yibei.yb.domain.bo.YbCommentsBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 评论Service接口
 *
 * @author yibei
 * @date 2022-05-19
 */
public interface IYbCommentsService extends IServicePlus<YbComments, YbCommentsVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbCommentsVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbCommentsVo> queryPageList(YbCommentsBo bo);

	/**
	 * 查询列表
	 */
	List<YbCommentsVo> queryList(YbCommentsBo bo);

	/**
	 * 根据新增业务对象插入评论
	 * @param bo 评论新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbCommentsBo bo);

	/**
	 * 根据编辑业务对象修改评论
	 * @param bo 评论编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbCommentsBo bo);

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
	YbCommentsVo queryExtData(YbCommentsVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbCommentsVo> queryExtData(List<YbCommentsVo> list);
}
