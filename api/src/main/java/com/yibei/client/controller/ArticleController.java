package com.yibei.client.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.cms.domain.CmsArticle;
import com.yibei.cms.domain.CmsCategory;
import com.yibei.cms.domain.bo.ArticleListBo;
import com.yibei.cms.domain.bo.CodeIdBo;
import com.yibei.cms.domain.bo.CodeBo;
import com.yibei.cms.domain.vo.CmsArticleVo;
import com.yibei.cms.service.ICmsArticleService;
import com.yibei.cms.service.ICmsCategoryService;
import com.yibei.common.core.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.utils.PageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Api(tags = "内容文章模块")
@RestController("ClienArticleController")
@RequestMapping("/client/articles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ArticleController extends BaseController {

    private final ICmsArticleService iCmsArticleService;
    private final ICmsCategoryService iCmsCategoryService;

    @ApiOperation("根据文章code/id获取详情")
    @PostMapping("/detailByCode")
    public AjaxResult getOneByCode(@RequestBody CodeIdBo vo)
    {
        LambdaQueryWrapper<CmsArticle> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CmsArticle::getStatus,1);
        lqw.orderByDesc(CmsArticle::getWeight);
        lqw.orderByDesc(CmsArticle::getId);
        lqw.eq(StringUtils.isNotBlank(vo.getCode()),CmsArticle::getCode,vo.getCode());
        lqw.eq(vo.getId()!=null,CmsArticle::getId,vo.getId());
        lqw.last("limit 1");
        CmsArticle one = iCmsArticleService.getOne(lqw);
        if(one==null){
            return AjaxResult.error("数据不存在");
        }
        return AjaxResult.success(one);
    }

    @ApiOperation("获取分页列表")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody ArticleListBo vo)
    {
        Long categoryId = null;
        if(StringUtils.isNotBlank(vo.getCategoryCode())){
            CmsCategory category = iCmsCategoryService.getOne(
                    new LambdaQueryWrapper<CmsCategory>()
                            .eq(CmsCategory::getCode, vo.getCategoryCode()).eq(CmsCategory::getIsDeleted,0)
                            .last("limit 1"));
            if(category==null){
                return AjaxResult.success("",new ArrayList<>(),0L);
            }
            categoryId = category.getId();
        }
        if(vo.getCategoryId()!=null){
            categoryId = vo.getCategoryId();
        }

        LambdaQueryWrapper<CmsArticle> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CmsArticle::getIsDeleted,0);
        lqw.eq(CmsArticle::getStatus,1);
        lqw.orderByDesc(CmsArticle::getWeight);
        lqw.orderByDesc(CmsArticle::getId);
        lqw.eq(categoryId!=null,CmsArticle::getCategoryId,categoryId);
        lqw.eq(vo.getIsTop()!=null,CmsArticle::getIsTop,vo.getIsTop());
        lqw.eq(vo.getIsRecommend()!=null,CmsArticle::getIsRecommend,vo.getIsRecommend());
        lqw.like(StringUtils.isNotBlank(vo.getKeyword()),CmsArticle::getTitle,vo.getKeyword());

		PagePlus<CmsArticle, CmsArticleVo> page =  iCmsArticleService.pageVo(PageUtils.buildPagePlus(),lqw);
        return AjaxResult.success("",page.getRecordsVo(),page.getTotal());
    }

    @ApiOperation("根据类型code获取子级类型列表")
    @PostMapping("/cateListByParentCode")
    public AjaxResult cateListByParentCode(@RequestBody CodeBo vo)
    {
        Long pid = null;
        if(StringUtils.isNotBlank(vo.getCode())){
            CmsCategory category = iCmsCategoryService.getOne(
                    new LambdaQueryWrapper<CmsCategory>()
                            .eq(CmsCategory::getCode, vo.getCode()).eq(CmsCategory::getIsDeleted,0)
                            .last("limit 1"));
            if(category==null){
                return AjaxResult.success("",new ArrayList<>(),0L);
            }
            pid = category.getId();
        }

        LambdaQueryWrapper<CmsCategory> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CmsCategory::getIsDeleted,0);
        lqw.eq(CmsCategory::getStatus,1);
        lqw.orderByDesc(CmsCategory::getWeight);
        lqw.orderByDesc(CmsCategory::getId);
        lqw.eq(CmsCategory::getParentId,pid);
        lqw.select(CmsCategory::getId,CmsCategory::getName);

        List<Map<String,Object>> list = iCmsCategoryService.listMaps(lqw);
        return AjaxResult.success("",list);
    }

    @ApiOperation("根据类型code获取文章列表(包含子级类型下的文章)")
    @PostMapping("/listByCode")
    public AjaxResult listByCode(@RequestBody CodeBo vo)
    {
        CmsCategory category = iCmsCategoryService.getOne(
                new LambdaQueryWrapper<CmsCategory>()
                        .eq(CmsCategory::getCode, vo.getCode()).eq(CmsCategory::getIsDeleted,0)
                        .last("limit 1"));
        if(category==null){
            return AjaxResult.success("",new ArrayList<>(),0L);
        }
        List<CmsCategory> cmsCategoryList = iCmsCategoryService.list(new LambdaQueryWrapper<CmsCategory>().eq(CmsCategory::getParentId,category.getId()).eq(CmsCategory::getIsDeleted,0).eq(CmsCategory::getStatus,1));

        LambdaQueryWrapper<CmsArticle> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CmsArticle::getIsDeleted,0);
        lqw.eq(CmsArticle::getStatus,1);
        lqw.orderByDesc(CmsArticle::getWeight);
        lqw.orderByDesc(CmsArticle::getId);

        String cids = cmsCategoryList.stream().map(obj -> obj.getId().toString()).collect(Collectors.joining(","));
        lqw.inSql(CmsArticle::getCategoryId,cids);

        List<CmsArticle> list = iCmsArticleService.list(lqw);
        return AjaxResult.success("",list);
    }
}
