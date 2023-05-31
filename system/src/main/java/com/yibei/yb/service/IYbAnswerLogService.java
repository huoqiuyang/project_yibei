package com.yibei.yb.service;

import com.yibei.yb.domain.YbAnswerLog;
import com.yibei.yb.domain.vo.YbAnswerLogVo;
import com.yibei.yb.domain.bo.YbAnswerLogBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 答题记录Service接口
 *
 * @author yibei
 * @date 2022-05-07
 */
public interface IYbAnswerLogService extends IServicePlus<YbAnswerLog, YbAnswerLogVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbAnswerLogVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbAnswerLogVo> queryPageList(YbAnswerLogBo bo);

	/**
	 * 查询列表
	 */
	List<YbAnswerLogVo> queryList(YbAnswerLogBo bo);

	/**
	 * 根据新增业务对象插入答题记录
	 * @param bo 答题记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbAnswerLogBo bo);

	/**
	 * 根据编辑业务对象修改答题记录
	 * @param bo 答题记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbAnswerLogBo bo);

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
	YbAnswerLogVo queryExtData(YbAnswerLogVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbAnswerLogVo> queryExtData(List<YbAnswerLogVo> list);
}
