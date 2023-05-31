package com.yibei.yb.service;

import com.yibei.yb.domain.YbTeachingMaterial;
import com.yibei.yb.domain.clientVo.*;
import com.yibei.yb.domain.vo.YbTeachingMaterialVo;
import com.yibei.yb.domain.bo.YbTeachingMaterialBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 教材Service接口
 *
 * @author yibei
 * @date 2022-05-11
 */
public interface IYbTeachingMaterialService extends IServicePlus<YbTeachingMaterial, YbTeachingMaterialVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbTeachingMaterialVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbTeachingMaterialVo> queryPageList(YbTeachingMaterialBo bo);

	/**
	 * 查询列表
	 */
	List<YbTeachingMaterialVo> queryList(YbTeachingMaterialBo bo);

	/**
	 * 根据新增业务对象插入教材
	 * @param bo 教材新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbTeachingMaterialBo bo);

	/**
	 * 根据编辑业务对象修改教材
	 * @param bo 教材编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbTeachingMaterialBo bo);

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
	YbTeachingMaterialVo queryExtData(YbTeachingMaterialVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbTeachingMaterialVo> queryExtData(List<YbTeachingMaterialVo> list);

	/**
	 * 学习报告-阅读进度
	 * */
	List<ReportStudyReadVo> getReportReadList(Long userId);

	/**
	 * 学习报告-背诵进度
	 * */
	List<ReportStudyReciteVo> getReportReciteList(Long userId);

	/**
	 * 学习报告-模拟测试
	 * */
	List<ReportStudyQuestionBankVo> getReportQuestionBankList(Long userId);

	/**
	 * 学习报告-拓展阅读
	 * */
	List<ReportStudyExpandReadingVo> getReportExpandReadingList(Long userId);

	/**
	 * 学习报告-近期学习记录
	 * */
	List<ReportStudyLogInfoVo> getReportStudyLogList(Long userId, int size);

}
