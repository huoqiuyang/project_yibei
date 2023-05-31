package com.yibei.cms.service;

import com.yibei.cms.domain.CmsArticle;
import com.yibei.cms.domain.vo.CmsArticleVo;
import com.yibei.cms.domain.bo.CmsArticleBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;
import com.yibei.common.core.page.TableDataInfo;

import java.util.Collection;
import java.util.List;

/**
 * 文章Service接口
 *
 * @author yibei
 * @date 2022-01-08
 */
public interface ICmsArticleService extends IServicePlus<CmsArticle, CmsArticleVo> {
	/**
	 * 查询单个
	 * @return
	 */
	CmsArticleVo queryById(Long id);

	/**
	 * 查询列表
	 */
    TableDataInfo<CmsArticleVo> queryPageList(CmsArticleBo bo);

	/**
	 * 查询列表
	 */
	List<CmsArticleVo> queryList(CmsArticleBo bo);

	/**
	 * 根据新增业务对象插入文章
	 * @param bo 文章新增业务对象
	 * @return
	 */
	Boolean insertByBo(CmsArticleBo bo);

	/**
	 * 根据编辑业务对象修改文章
	 * @param bo 文章编辑业务对象
	 * @return
	 */
	Boolean updateByBo(CmsArticleBo bo);

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
	CmsArticleVo queryExtData(CmsArticleVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<CmsArticleVo> queryExtData(List<CmsArticleVo> list);
}
