package com.yibei.yb.service;

import com.yibei.yb.domain.YbStudyConfig;
import com.yibei.yb.domain.vo.YbStudyConfigVo;
import com.yibei.yb.domain.bo.YbStudyConfigBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 教材学习配置Service接口
 *
 * @author yibei
 * @date 2022-05-12
 */
public interface IYbStudyConfigService extends IServicePlus<YbStudyConfig, YbStudyConfigVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbStudyConfigVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbStudyConfigVo> queryPageList(YbStudyConfigBo bo);

	/**
	 * 查询列表
	 */
	List<YbStudyConfigVo> queryList(YbStudyConfigBo bo);

	/**
	 * 根据新增业务对象插入教材学习配置
	 * @param bo 教材学习配置新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbStudyConfigBo bo);

	/**
	 * 根据编辑业务对象修改教材学习配置
	 * @param bo 教材学习配置编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbStudyConfigBo bo);

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
	YbStudyConfigVo queryExtData(YbStudyConfigVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbStudyConfigVo> queryExtData(List<YbStudyConfigVo> list);
}
