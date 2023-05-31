package com.yibei.yb.service;

import com.yibei.yb.domain.YbStudyTimeLog;
import com.yibei.yb.domain.clientVo.TranscendVo;
import com.yibei.yb.domain.vo.YbStudyTimeLogVo;
import com.yibei.yb.domain.bo.YbStudyTimeLogBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 学习时间记录Service接口
 *
 * @author yibei
 * @date 2022-05-18
 */
public interface IYbStudyTimeLogService extends IServicePlus<YbStudyTimeLog, YbStudyTimeLogVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbStudyTimeLogVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbStudyTimeLogVo> queryPageList(YbStudyTimeLogBo bo);

	/**
	 * 查询列表
	 */
	List<YbStudyTimeLogVo> queryList(YbStudyTimeLogBo bo);

	/**
	 * 根据新增业务对象插入学习时间记录
	 * @param bo 学习时间记录新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbStudyTimeLogBo bo);

	/**
	 * 根据编辑业务对象修改学习时间记录
	 * @param bo 学习时间记录编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbStudyTimeLogBo bo);

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
	YbStudyTimeLogVo queryExtData(YbStudyTimeLogVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbStudyTimeLogVo> queryExtData(List<YbStudyTimeLogVo> list);

	/**
	 * 获取用户学习时间
	 * */
	Integer getUserStudyMin(Long userId,String startTime,String endTime);

	/**
	 * 获取用户当日学习时长超越人数相关信息
	 * */
	TranscendVo getTranscendInfo(Long userId, String startTime, String endTime,int userStudyMin);

}
