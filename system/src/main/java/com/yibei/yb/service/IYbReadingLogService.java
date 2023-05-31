package com.yibei.yb.service;

import com.yibei.yb.domain.YbReadingLog;
import com.yibei.yb.domain.vo.YbReadingLogVo;
import com.yibei.yb.domain.bo.YbReadingLogBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 阅读记录Service接口
 *
 * @author yibei
 * @date 2022-05-11
 */
public interface IYbReadingLogService extends IServicePlus<YbReadingLog, YbReadingLogVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbReadingLogVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbReadingLogVo> queryPageList(YbReadingLogBo bo);

	/**
	 * 查询列表
	 */
	List<YbReadingLogVo> queryList(YbReadingLogBo bo);

	/**
	 * 根据新增业务对象插入阅读记录
	 * @param bo 阅读记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbReadingLogBo bo);

	/**
	 * 根据编辑业务对象修改阅读记录
	 * @param bo 阅读记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbReadingLogBo bo);

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
	YbReadingLogVo queryExtData(YbReadingLogVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbReadingLogVo> queryExtData(List<YbReadingLogVo> list);
}
