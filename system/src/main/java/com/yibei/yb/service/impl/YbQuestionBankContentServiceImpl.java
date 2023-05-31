package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yibei.yb.domain.YbQuestionBank;
import com.yibei.yb.domain.clientVo.*;
import com.yibei.yb.domain.vo.YbQuestionBankVo;
import com.yibei.yb.service.IYbQuestionBankService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbQuestionBankContentBo;
import com.yibei.yb.domain.vo.YbQuestionBankContentVo;
import com.yibei.yb.domain.YbQuestionBankContent;
import com.yibei.yb.mapper.YbQuestionBankContentMapper;
import com.yibei.yb.service.IYbQuestionBankContentService;

import com.yibei.common.utils.StringUtils;

import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 题库内容Service业务层处理
 *
 * @author yibei
 * @date 2022-05-06
 */
@Service
public class YbQuestionBankContentServiceImpl extends ServicePlusImpl<YbQuestionBankContentMapper, YbQuestionBankContent, YbQuestionBankContentVo> implements IYbQuestionBankContentService {

    @Resource
    private IYbQuestionBankService iYbQuestionBankService;

    @Override
    public YbQuestionBankContentVo queryById(Long id){
        return getVoById(id);
    }


    @Override
    public List<YbQuestionBankContentVo> queryList(YbQuestionBankContentBo bo) {
        return listVo(buildQueryWrapper(bo));
    }
    @Override
    public List<YbQuestionBankContentTitleVo> selectList(YbQuestionBankContentBo bo) {
        List<YbQuestionBankContentVo> list = listVo(buildQueryWrapper(bo));
        return BeanUtil.copyToList(list, YbQuestionBankContentTitleVo.class);
    }

    private LambdaQueryWrapper<YbQuestionBankContent> buildQueryWrapper(YbQuestionBankContentBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbQuestionBankContent> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(YbQuestionBankContent::getSort);
        lqw.orderByDesc(YbQuestionBankContent::getCreateTime);
//        lqw.orderByDesc(YbQuestionBankContent::getId);
        lqw.eq(bo.getQuestionBankId() != null, YbQuestionBankContent::getQuestionBankId, bo.getQuestionBankId());
        lqw.eq(bo.getParentId() != null, YbQuestionBankContent::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), YbQuestionBankContent::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getAnswer()), YbQuestionBankContent::getAnswer, bo.getAnswer());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrl()), YbQuestionBankContent::getImgUrl, bo.getImgUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getLabel()), YbQuestionBankContent::getLabel, bo.getLabel());
        lqw.eq(bo.getImportance()!=null, YbQuestionBankContent::getImportance, bo.getImportance());
        lqw.eq(bo.getSort() != null, YbQuestionBankContent::getSort, bo.getSort());
        lqw.eq(bo.getStatus() != null, YbQuestionBankContent::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbQuestionBankContentBo bo) {
        YbQuestionBankContent add = BeanUtil.toBean(bo, YbQuestionBankContent.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbQuestionBankContentBo bo) {
        YbQuestionBankContent update = BeanUtil.toBean(bo, YbQuestionBankContent.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbQuestionBankContent entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public YbQuestionBankContentVo queryExtData(YbQuestionBankContentVo vo) {
        List<YbQuestionBankContentVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbQuestionBankContentVo> queryExtData(List<YbQuestionBankContentVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String questionBankIds = list.stream().map(s -> s.getQuestionBankId().toString()).collect(Collectors.joining(","));
        questionBankIds = StringUtils.isBlank(questionBankIds)?"0":questionBankIds;
        List<YbQuestionBankVo> ybQuestionBankVoList = iYbQuestionBankService.listVo(new LambdaQueryWrapper<YbQuestionBank>().inSql(YbQuestionBank::getId, questionBankIds));
        list.forEach(item->{
        item.getExtData().put("ybQuestionBank", ybQuestionBankVoList.stream().filter(s -> s.getId().compareTo(item.getQuestionBankId()) == 0).findFirst().orElse(null));
        });

        return list;
    }

    @Override
    public List<YbQuestionBankContent> getListBysort(Long questionBankId) {
        return baseMapper.getListBysort(questionBankId);
    }

    @Override
    public List<YbQbcCatalogueInfoVo> getCollectionList(Long userId, Long questionBankId) {
        return baseMapper.getCollectionList(userId,questionBankId);
    }

    @Override
    public List<YbQbcCatalogueSecondInfoVo> getSecondList(Long userId, Long questionBankContentId,Integer isCollection) {
        return baseMapper.getSecondList(userId,questionBankContentId,isCollection);
    }

    @Override
    public YbQuestionBankContentInfoVo getSubjectInfo(Long questionBankId, Long selectId) {
        return baseMapper.getSubjectInfo(questionBankId,selectId);
    }

    @Override
    public long getAnswersNum(Long questionBankId, Long userId, Integer type) {
        return baseMapper.getAnswersNum(questionBankId,userId,type);
    }

    @Override
    public Map<String, BigDecimal> getAnswerInfo(Long questionBankId, Long userId) {
        return baseMapper.getAnswerInfo(questionBankId,userId);
    }
}
