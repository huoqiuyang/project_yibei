package com.yibei.yb.service;

import com.yibei.yb.domain.YbReportErrors;
import com.yibei.yb.domain.vo.YbReportErrorsVo;
import com.yibei.yb.domain.bo.YbReportErrorsBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 报错Service接口
 *
 * @author yibei
 * @date 2022-05-18
 */
public interface IYbReportErrorsService extends IServicePlus<YbReportErrors, YbReportErrorsVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbReportErrorsVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbReportErrorsVo> queryPageList(YbReportErrorsBo bo);

	/**
	 * 查询列表
	 */
	List<YbReportErrorsVo> queryList(YbReportErrorsBo bo);

	/**
	 * 根据新增业务对象插入报错
	 * @param bo 报错新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbReportErrorsBo bo);

	/**
	 * 根据编辑业务对象修改报错
	 * @param bo 报错编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbReportErrorsBo bo);

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
	YbReportErrorsVo queryExtData(YbReportErrorsVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbReportErrorsVo> queryExtData(List<YbReportErrorsVo> list);
}
