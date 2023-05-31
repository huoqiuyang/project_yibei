package com.yibei.system.service;

import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.SysOssConfig;
import com.yibei.system.domain.bo.SysOssConfigBo;
import com.yibei.system.domain.vo.SysOssConfigVo;

import java.util.Collection;

/**
 * 云存储配置Service接口
 *
 * @author Lion Li
 * @author 孤舟烟雨
 * @date 2021-08-13
 */
public interface ISysOssConfigService extends IServicePlus<SysOssConfig, SysOssConfigVo> {

	/**
	 * 查询单个
	 */
	SysOssConfigVo queryById(Integer ossConfigId);

	/**
	 * 查询列表
	 */
    TableDataInfo<SysOssConfigVo> queryPageList(SysOssConfigBo bo);


	/**
	 * 根据新增业务对象插入云存储配置
	 * @param bo 云存储配置新增业务对象
	 * @return
	 */
	Boolean insertByBo(SysOssConfigBo bo);

	/**
	 * 根据编辑业务对象修改云存储配置
	 * @param bo 云存储配置编辑业务对象
	 * @return
	 */
	Boolean updateByBo(SysOssConfigBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

	/**
	 * 启用停用状态
	 */
	int updateOssConfigStatus(SysOssConfigBo bo);

}
