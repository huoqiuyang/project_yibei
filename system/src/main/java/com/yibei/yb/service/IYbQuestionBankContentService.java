package com.yibei.yb.service;

import com.yibei.yb.domain.YbQuestionBankContent;
import com.yibei.yb.domain.clientVo.YbQbcCatalogueInfoVo;
import com.yibei.yb.domain.clientVo.YbQbcCatalogueSecondInfoVo;
import com.yibei.yb.domain.clientVo.YbQuestionBankContentInfoVo;
import com.yibei.yb.domain.clientVo.YbQuestionBankContentTitleVo;
import com.yibei.yb.domain.vo.YbQuestionBankContentVo;
import com.yibei.yb.domain.bo.YbQuestionBankContentBo;
import com.yibei.common.core.mybatisplus.core.IServicePlus;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 题库内容Service接口
 *
 * @author yibei
 * @date 2022-05-06
 */
public interface IYbQuestionBankContentService extends IServicePlus<YbQuestionBankContent, YbQuestionBankContentVo> {
	/**
	 * 查询单个
	 * @return
	 */
	YbQuestionBankContentVo queryById(Long id);


	/**
	 * 查询列表
	 */
	List<YbQuestionBankContentVo> queryList(YbQuestionBankContentBo bo);
	List<YbQuestionBankContentTitleVo> selectList(YbQuestionBankContentBo bo);

	/**
	 * 根据新增业务对象插入题库内容
	 * @param bo 题库内容新增业务对象
	 * @return
	 */
	Boolean insertByBo(YbQuestionBankContentBo bo);

	/**
	 * 根据编辑业务对象修改题库内容
	 * @param bo 题库内容编辑业务对象
	 * @return
	 */
	Boolean updateByBo(YbQuestionBankContentBo bo);

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
	YbQuestionBankContentVo queryExtData(YbQuestionBankContentVo vo);

	/**
    * 查询关联数据
    * @return
    */
	List<YbQuestionBankContentVo> queryExtData(List<YbQuestionBankContentVo> list);

	/**
	 * 根据排序查询题库里的题目列表
	 * */
	List<YbQuestionBankContent> getListBysort(Long questionBankId);

	/**
	 * 收藏目录标题
	 * */
	List<YbQbcCatalogueInfoVo> getCollectionList(Long userId, Long questionBankId);

	/**
	 * 二级目录信息
	 * */
	List<YbQbcCatalogueSecondInfoVo> getSecondList(Long userId,Long questionBankContentId,Integer isCollection);

	/**
	 * 题目详情
	 * @param questionBankId 题库id
	 * @param selectId 题目id
	 * */
	YbQuestionBankContentInfoVo getSubjectInfo(Long questionBankId, Long selectId);

	/**
	 *
	 * @param questionBankId 题库id
	 * @param userId 用户id
	 * @param type 答题类型（1知道2不知道）
	 * */
	long getAnswersNum(Long questionBankId, Long userId,Integer type);

	Map<String, BigDecimal> getAnswerInfo(Long questionBankId, Long userId);

}
