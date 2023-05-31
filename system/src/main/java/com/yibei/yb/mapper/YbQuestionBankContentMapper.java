package com.yibei.yb.mapper;

import com.yibei.yb.domain.YbQuestionBankContent;
import com.yibei.common.core.mybatisplus.core.BaseMapperPlus;
import com.yibei.yb.domain.clientVo.YbQbcCatalogueInfoVo;
import com.yibei.yb.domain.clientVo.YbQbcCatalogueSecondInfoVo;
import com.yibei.yb.domain.clientVo.YbQuestionBankContentInfoVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 题库内容Mapper接口
 *
 * @author yibei
 * @date 2022-05-06
 */
public interface YbQuestionBankContentMapper extends BaseMapperPlus<YbQuestionBankContent> {

    /**
     * 根据排序查询题库里的题目列表
     * */
    List<YbQuestionBankContent> getListBysort(@Param("questionBankId")Long questionBankId);

    /**
     * 收藏目录标题
     * */
    List<YbQbcCatalogueInfoVo> getCollectionList(@Param("userId")Long userId, @Param("questionBankId")Long questionBankId);

    /**
     * 二级目录信息
     * */
    List<YbQbcCatalogueSecondInfoVo> getSecondList(@Param("userId")Long userId, @Param("questionBankContentId")Long questionBankContentId,@Param("isCollection")Integer isCollection);

    /**
     * 题目详情
     * @param questionBankId 题库id
     * @param selectId 题目id
     * */
    YbQuestionBankContentInfoVo getSubjectInfo(@Param("questionBankId")Long questionBankId, @Param("selectId")Long selectId);

    /**
     *
     * @param questionBankId 题库id
     * @param userId 用户id
     * @param type 答题类型（1知道2不知道）
     * */
    long getAnswersNum(@Param("questionBankId")Long questionBankId, @Param("userId")Long userId,@Param("type")Integer type);

    Map<String, BigDecimal> getAnswerInfo(@Param("questionBankId")Long questionBankId, @Param("userId")Long userId);

}
