package com.yibei.yb.service;

import com.yibei.yb.domain.YbContentLink;
import com.yibei.yb.domain.vo.YbContentLinkVo;
import com.yibei.yb.domain.bo.YbContentLinkBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 相关链接Service接口
 *
 * @author yibei
 * @date 2022-05-06
 */
public interface IYbContentLinkService extends IServicePlus<YbContentLink, YbContentLinkVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbContentLinkVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<YbContentLinkVo> queryPageList(YbContentLinkBo bo);

	/**
	 * 查询列表
	 */
	List<YbContentLinkVo> queryList(YbContentLinkBo bo);

	/**
	 * 根据新增业务对象插入相关链接
	 * @param bo 相关链接新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbContentLinkBo bo);

	/**
	 * 根据编辑业务对象修改相关链接
	 * @param bo 相关链接编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbContentLinkBo bo);

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
	YbContentLinkVo queryExtData(YbContentLinkVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbContentLinkVo> queryExtData(List<YbContentLinkVo> list);
}
