package com.yibei.yb.service;

import com.yibei.yb.domain.YbExpandReading;
import com.yibei.yb.domain.vo.YbExpandReadingVo;
import com.yibei.yb.domain.bo.YbExpandReadingBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 拓展阅读Service接口
 *
 * @author yibei
 * @date 2022-05-11
 */
public interface IYbExpandReadingService extends IServicePlus<YbExpandReading, YbExpandReadingVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbExpandReadingVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbExpandReadingVo> queryPageList(YbExpandReadingBo bo);

	/**
	 * 查询列表
	 */
	List<YbExpandReadingVo> queryList(YbExpandReadingBo bo);

	/**
	 * 根据新增业务对象插入拓展阅读
	 * @param bo 拓展阅读新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbExpandReadingBo bo);

	/**
	 * 根据编辑业务对象修改拓展阅读
	 * @param bo 拓展阅读编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbExpandReadingBo bo);

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
	YbExpandReadingVo queryExtData(YbExpandReadingVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbExpandReadingVo> queryExtData(List<YbExpandReadingVo> list);
}
