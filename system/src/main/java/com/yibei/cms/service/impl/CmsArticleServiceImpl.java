package com.yibei.cms.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.cms.domain.bo.CmsArticleBo;
import com.yibei.cms.domain.vo.CmsArticleVo;
import com.yibei.cms.domain.CmsArticle;
import com.yibei.cms.mapper.CmsArticleMapper;
import com.yibei.cms.service.ICmsArticleService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 文章Service业务层处理
 *
 * @author yibei
 * @date 2022-01-08
 */
@Service
public class CmsArticleServiceImpl extends ServicePlusImpl<CmsArticleMapper, CmsArticle, CmsArticleVo> implements ICmsArticleService {


    @Override
    public CmsArticleVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<CmsArticleVo> queryPageList(CmsArticleBo bo) {
        PagePlus<CmsArticle, CmsArticleVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<CmsArticleVo> queryList(CmsArticleBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<CmsArticle> buildQueryWrapper(CmsArticleBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CmsArticle> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(CmsArticle::getId);
        lqw.eq(bo.getCategoryId() != null, CmsArticle::getCategoryId, bo.getCategoryId());
        lqw.eq(StringUtils.isNotBlank(bo.getCategoryPath()), CmsArticle::getCategoryPath, bo.getCategoryPath());
        lqw.eq(StringUtils.isNotBlank(bo.getCode()), CmsArticle::getCode, bo.getCode());
        lqw.eq(StringUtils.isNotBlank(bo.getTitle()), CmsArticle::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getSubTitle()), CmsArticle::getSubTitle, bo.getSubTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getBakTitle()), CmsArticle::getBakTitle, bo.getBakTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getDescription()), CmsArticle::getDescription, bo.getDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getBakDescription()), CmsArticle::getBakDescription, bo.getBakDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getLinkUrl()), CmsArticle::getLinkUrl, bo.getLinkUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getSubLinkUrl()), CmsArticle::getSubLinkUrl, bo.getSubLinkUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrl()), CmsArticle::getImgUrl, bo.getImgUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getImgsUrl()), CmsArticle::getImgsUrl, bo.getImgsUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getVideoUrl()), CmsArticle::getVideoUrl, bo.getVideoUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getAttachment()), CmsArticle::getAttachment, bo.getAttachment());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), CmsArticle::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getContentBak()), CmsArticle::getContentBak, bo.getContentBak());
        lqw.eq(StringUtils.isNotBlank(bo.getMetaTitle()), CmsArticle::getMetaTitle, bo.getMetaTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getMetaDescription()), CmsArticle::getMetaDescription, bo.getMetaDescription());
        lqw.eq(StringUtils.isNotBlank(bo.getMetaKeywords()), CmsArticle::getMetaKeywords, bo.getMetaKeywords());
        lqw.eq(StringUtils.isNotBlank(bo.getMetaTitleBak()), CmsArticle::getMetaTitleBak, bo.getMetaTitleBak());
        lqw.eq(StringUtils.isNotBlank(bo.getMetaDescriptionBak()), CmsArticle::getMetaDescriptionBak, bo.getMetaDescriptionBak());
        lqw.eq(StringUtils.isNotBlank(bo.getMetaKeywordsBak()), CmsArticle::getMetaKeywordsBak, bo.getMetaKeywordsBak());
        lqw.eq(StringUtils.isNotBlank(bo.getAuthor()), CmsArticle::getAuthor, bo.getAuthor());
        lqw.eq(StringUtils.isNotBlank(bo.getSource()), CmsArticle::getSource, bo.getSource());
        lqw.eq(bo.getPublishTime() != null, CmsArticle::getPublishTime, bo.getPublishTime());
        lqw.eq(bo.getHitCount() != null, CmsArticle::getHitCount, bo.getHitCount());
        lqw.eq(bo.getSort() != null, CmsArticle::getSort, bo.getSort());
        lqw.eq(bo.getWeight() != null, CmsArticle::getWeight, bo.getWeight());
        lqw.eq(bo.getIsTop() != null, CmsArticle::getIsTop, bo.getIsTop());
        lqw.eq(bo.getIsRecommend() != null, CmsArticle::getIsRecommend, bo.getIsRecommend());
        lqw.eq(bo.getStatus() != null, CmsArticle::getStatus, bo.getStatus());
        if(StringUtils.isNotBlank(bo.getKeyword())){
            lqw.like(CmsArticle::getTitle,bo.getKeyword());
        }
        return lqw;
    }

    @Override
    public Boolean insertByBo(CmsArticleBo bo) {
        CmsArticle add = BeanUtil.toBean(bo, CmsArticle.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(CmsArticleBo bo) {
        CmsArticle update = BeanUtil.toBean(bo, CmsArticle.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(CmsArticle entity){
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
    public CmsArticleVo queryExtData(CmsArticleVo vo) {
        List<CmsArticleVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<CmsArticleVo> queryExtData(List<CmsArticleVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }
}
