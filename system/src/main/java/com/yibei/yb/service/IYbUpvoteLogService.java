package com.yibei.yb.service;

import com.yibei.yb.domain.YbUpvoteLog;
import com.yibei.yb.domain.vo.YbUpvoteLogVo;
import com.yibei.yb.domain.bo.YbUpvoteLogBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 点赞记录Service接口
 *
 * @author yibei
 * @date 2022-05-19
 */
public interface IYbUpvoteLogService extends IServicePlus<YbUpvoteLog, YbUpvoteLogVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbUpvoteLogVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbUpvoteLogVo> queryPageList(YbUpvoteLogBo bo);

	/**
	 * 查询列表
	 */
	List<YbUpvoteLogVo> queryList(YbUpvoteLogBo bo);

	/**
	 * 根据新增业务对象插入点赞记录
	 * @param bo 点赞记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbUpvoteLogBo bo);

	/**
	 * 根据编辑业务对象修改点赞记录
	 * @param bo 点赞记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbUpvoteLogBo bo);

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
	YbUpvoteLogVo queryExtData(YbUpvoteLogVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbUpvoteLogVo> queryExtData(List<YbUpvoteLogVo> list);
}
