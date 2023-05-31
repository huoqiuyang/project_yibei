package com.yibei.yb.service;

import com.yibei.yb.domain.YbNote;
import com.yibei.yb.domain.vo.YbNoteVo;
import com.yibei.yb.domain.bo.YbNoteBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 笔记Service接口
 *
 * @author yibei
 * @date 2022-05-11
 */
public interface IYbNoteService extends IServicePlus<YbNote, YbNoteVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbNoteVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbNoteVo> queryPageList(YbNoteBo bo);

	/**
	 * 查询列表
	 */
	List<YbNoteVo> queryList(YbNoteBo bo);

	/**
	 * 根据新增业务对象插入笔记
	 * @param bo 笔记新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbNoteBo bo);

	/**
	 * 根据编辑业务对象修改笔记
	 * @param bo 笔记编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbNoteBo bo);

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
	YbNoteVo queryExtData(YbNoteVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbNoteVo> queryExtData(List<YbNoteVo> list);
}
