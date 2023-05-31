package com.yibei.yb.service;

import com.yibei.yb.domain.VLink;
import com.yibei.yb.domain.vo.VLinkVo;
import com.yibei.yb.domain.bo.VLinkBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 相关链接Service接口
 *
 * @author yibei
 * @date 2022-06-09
 */
public interface IVLinkService extends IServicePlus<VLink, VLinkVo> {
	/**
	 * 查询单个
	 * @return
	 */
	VLinkVo queryById(String type);

	/**
	 * 查询列表
	 */
    TableDataInfo<VLinkVo> queryPageList(VLinkBo bo);

	/**
	 * 查询列表
	 */
	List<VLinkVo> queryList(VLinkBo bo);

	/**
	 * 根据新增业务对象插入相关链接
	 * @param bo 相关链接新增业务对象
	 * @return
	 */
	Boolean insertByBo(VLinkBo bo);

	/**
	 * 根据编辑业务对象修改相关链接
	 * @param bo 相关链接编辑业务对象
	 * @return
	 */
	Boolean updateByBo(VLinkBo bo);

	/**
	 * 校验并删除数据
	 * @param ids 主键集合
	 * @param isValid 是否校验,true-删除前校验,false-不校验
	 * @return
	 */
	Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid);

	/**
    * 查询关联数据
    * @return
    */
	VLinkVo queryExtData(VLinkVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<VLinkVo> queryExtData(List<VLinkVo> list);
}
