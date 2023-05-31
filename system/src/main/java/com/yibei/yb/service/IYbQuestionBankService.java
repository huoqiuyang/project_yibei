package com.yibei.yb.service;

import com.yibei.yb.domain.YbQuestionBank;
import com.yibei.yb.domain.vo.YbQuestionBankVo;
import com.yibei.yb.domain.bo.YbQuestionBankBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 题库Service接口
 *
 * @author yibei
 * @date 2022-05-06
 */
public interface IYbQuestionBankService extends IServicePlus<YbQuestionBank, YbQuestionBankVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbQuestionBankVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbQuestionBankVo> queryPageList(YbQuestionBankBo bo);

	/**
	 * 查询列表
	 */
	List<YbQuestionBankVo> queryList(YbQuestionBankBo bo);

	/**
	 * 根据新增业务对象插入题库
	 * @param bo 题库新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbQuestionBankBo bo);

	/**
	 * 根据编辑业务对象修改题库
	 * @param bo 题库编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbQuestionBankBo bo);

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
	YbQuestionBankVo queryExtData(YbQuestionBankVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbQuestionBankVo> queryExtData(List<YbQuestionBankVo> list);
}
