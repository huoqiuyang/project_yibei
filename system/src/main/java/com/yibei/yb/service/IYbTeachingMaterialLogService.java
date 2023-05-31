package com.yibei.yb.service;

import com.yibei.yb.domain.YbTeachingMaterialLog;
import com.yibei.yb.domain.vo.YbTeachingMaterialLogVo;
import com.yibei.yb.domain.bo.YbTeachingMaterialLogBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 教材阅读背诵记录Service接口
 *
 * @author yibei
 * @date 2022-05-12
 */
public interface IYbTeachingMaterialLogService extends IServicePlus<YbTeachingMaterialLog, YbTeachingMaterialLogVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbTeachingMaterialLogVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbTeachingMaterialLogVo> queryPageList(YbTeachingMaterialLogBo bo);

	/**
	 * 查询列表
	 */
	List<YbTeachingMaterialLogVo> queryList(YbTeachingMaterialLogBo bo);

	/**
	 * 根据新增业务对象插入教材阅读背诵记录
	 * @param bo 教材阅读背诵记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbTeachingMaterialLogBo bo);

	/**
	 * 根据编辑业务对象修改教材阅读背诵记录
	 * @param bo 教材阅读背诵记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbTeachingMaterialLogBo bo);

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
	YbTeachingMaterialLogVo queryExtData(YbTeachingMaterialLogVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbTeachingMaterialLogVo> queryExtData(List<YbTeachingMaterialLogVo> list);
}
