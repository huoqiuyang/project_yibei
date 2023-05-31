package com.yibei.yb.service;

import com.yibei.yb.domain.YbTeachingMaterialEntry;
import com.yibei.yb.domain.clientVo.*;
import com.yibei.yb.domain.vo.YbTeachingMaterialEntryVo;
import com.yibei.yb.domain.bo.YbTeachingMaterialEntryBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;

import java.util.Collection;
import java.util.List;

/**
 * 教材词条Service接口
 *
 * @author yibei
 * @date 2022-05-14
 */
public interface IYbTeachingMaterialEntryService extends IServicePlus<YbTeachingMaterialEntry, YbTeachingMaterialEntryVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbTeachingMaterialEntryVo queryById(Long id);


	/**
	 * 查询列表
	 */
	List<YbTeachingMaterialEntryVo> queryList(YbTeachingMaterialEntryBo bo);
	List<YbTeachingMaterialEntryTitleVo> selectList(YbTeachingMaterialEntryBo bo);

	/**
	 * 根据新增业务对象插入教材词条
	 * @param bo 教材词条新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbTeachingMaterialEntryBo bo);

	/**
	 * 根据编辑业务对象修改教材词条
	 * @param bo 教材词条编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbTeachingMaterialEntryBo bo);

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
	YbTeachingMaterialEntryVo queryExtData(YbTeachingMaterialEntryVo vo);

	/**
	 * 查询关联数据
	 * @return
	 */
	List<YbTeachingMaterialEntryVo> queryExtData(List<YbTeachingMaterialEntryVo> list);

	/**
	 * 目录标题
	 * */
	List<YbTmeCatalogueInfoVo> getCollectionList(Long userId, Long teachingMaterialId);

	/**
	 * 二级目录信息
	 *
	 * */
	List<YbTmeCatalogueSecondInfoVo> getSecondList(Long userId,Long teachingMaterialEntryId,Integer isCollection);

	/**
	 * 最近学习记录查询
	 * @param teachingMaterialId 教材id
	 * @param userId 用户id
	 * @param learningType 学习类型 1阅读2背诵
	 * */
	YbTeachingMaterialEntry getEntryLog(Long teachingMaterialId,Long userId,int learningType);

	/**
	 * 词条详情(阅读模式)
	 * @param teachingMaterialId 教材id
	 * @param selectId 词条id
	 * */
	YbTeachingMaterialEntryReadInfoVo getEntryReadInfo(Long teachingMaterialId, Long selectId);

	/**
	 * 已读词条数
	 * @param teachingMaterialId 教材id
	 * @param userId 用户id
	 * */
	long getProportionNum(Long teachingMaterialId, Long userId);

	/**
	 * 我的背诵词条数
	 * @param teachingMaterialId 教材id
	 * @param userId 用户id
	 * */
	long getIsReciteNum(Long teachingMaterialId, Long userId);


	/**
	 * 我的背诵词条数
	 * @param userId 用户id
	 * @param teachingMaterialId 教材id
	 * @param studyLimit 学习数量
	 * @param reviewLimit 复习数量
	 * */
	List<YbTeachingMaterialEntryReciteInfoVo> getToDayRecitePlanList(Long userId,Long teachingMaterialId,Integer studyLimit,Integer reviewLimit);

	int getReciteEntryList(Long userId,Long teachingMaterialId);
}