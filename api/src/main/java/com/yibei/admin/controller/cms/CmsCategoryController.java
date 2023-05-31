package com.yibei.admin.controller.cms;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.cms.domain.CmsArticle;
import com.yibei.cms.domain.CmsCategory;
import com.yibei.cms.domain.CmsCategoryTreeNode;
import com.yibei.common.annotation.Excel;
import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import com.yibei.common.annotation.RepeatSubmit;
import com.yibei.common.annotation.Log;
import com.yibei.common.core.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.validate.AddGroup;
import com.yibei.common.core.validate.EditGroup;
import com.yibei.common.core.validate.QueryGroup;
import com.yibei.common.enums.BusinessType;
import com.yibei.common.utils.poi.ExcelUtil;
import com.yibei.cms.domain.vo.CmsCategoryVo;
import com.yibei.cms.domain.bo.CmsCategoryBo;
import com.yibei.cms.service.ICmsCategoryService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 内容分类Controller
 *
 * @author yibei
 * @date 2022-01-08
 */
@Validated
@Api(value = "内容分类控制器", tags = {"内容分类管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/cms/category")
public class CmsCategoryController extends BaseController {

    private final ICmsCategoryService iCmsCategoryService;


    @ApiOperation("获取文章类型下拉树列表")
    @GetMapping("/treeSelect")
    public AjaxResult treeSelect(@RequestParam(required = false) String code)
    {
        LambdaQueryWrapper<CmsCategory> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CmsCategory::getIsDeleted,0);
        lqw.eq(CmsCategory::getStatus,1);
        lqw.eq(StringUtils.isNotBlank(code),CmsCategory::getCode,code);
        lqw.orderByDesc(CmsCategory::getWeight);
        lqw.orderByAsc(CmsCategory::getId);
        List<CmsCategory> list = iCmsCategoryService.list(lqw);
        List<CmsCategoryTreeNode> tree = new ArrayList<>();

        for (CmsCategory category : list) {
            //找到根节点
            if (category.getParentId().compareTo(0L)==0) {
                CmsCategoryTreeNode node = new CmsCategoryTreeNode();
                node.setId(category.getId());
                node.setName(category.getName());
                node.setParentId(0L);
                node.setCreateChildEnable(category.getCreateChildEnable());
                node.setChildTemplateId(category.getChildTemplateId());
                tree.add(findChildren(node, list));
            }
        }

        return AjaxResult.success(tree);
    }

    private CmsCategoryTreeNode findChildren(CmsCategoryTreeNode node, List<CmsCategory> list) {
        List<CmsCategoryTreeNode> children = new ArrayList<>();
        for (CmsCategory category : list) {
            if (category.getParentId().compareTo(node.getId())==0) {
                CmsCategoryTreeNode node2 = new CmsCategoryTreeNode();
                node2.setId(category.getId());
                node2.setName(category.getName());
                node2.setParentId(node.getId());
                //递归调用
                children.add(findChildren(node2, list));
            }
        }
        node.setChildren(children);
        return node;
    }

    /**
     * 查询内容分类列表
     */
    @ApiOperation("查询内容分类列表")
    @GetMapping("/list")
    public AjaxResult<List<CmsCategoryVo>> list(@Validated(QueryGroup.class) CmsCategoryBo bo) {
        List<CmsCategoryVo> list = iCmsCategoryService.queryList(bo);
        return AjaxResult.success(list);
    }

    /**
     * 导出内容分类列表
     */
    @ApiOperation("导出内容分类列表")
    @Log(title = "内容分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated CmsCategoryBo bo, HttpServletResponse response) {
        List<CmsCategoryVo> list = iCmsCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "内容分类", CmsCategoryVo.class, response);
    }

    /**
     * 获取内容分类详细信息
     */
    @ApiOperation("获取内容分类详细信息")
    @GetMapping("/{id}")
    public AjaxResult<CmsCategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                             @PathVariable("id") Long id) {
        CmsCategoryVo vo = iCmsCategoryService.queryById(id);
        //vo = iCmsCategoryService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增内容分类
     */
    /*@ApiOperation("新增内容分类")
    @Log(title = "内容分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody CmsCategoryBo bo) {

        //判断分类父级是否开启自定义添加子分类
        if(bo.getParentId()!=null && bo.getParentId().compareTo(0L)==1){
            CmsCategory parent = iCmsCategoryService.getById(bo.getParentId());
            if(parent==null || parent.getCreateChildEnable().compareTo(0L)==0){
                return toAjax(0);
            }
            //查询模板分类
            CmsCategory temp = iCmsCategoryService.getById(parent.getChildTemplateId());
            if(temp==null){
                return toAjax(0);
            }
            bo.setCustomProperty(temp.getCustomProperty());
            bo.setCreateDataEnable(temp.getCreateDataEnable());
            bo.setCreateChildEnable(temp.getCreateChildEnable());
        }
        return toAjax(iCmsCategoryService.insertByBo(bo) ? 1 : 0);
    }*/

    /**
     * 新增文章类型
     */
    @ApiOperation("新增文章类型")
//    @PreAuthorize("@ss.hasAnyPermi('cms:category:add,cms:article:add')" )
    @Log(title = "文章类型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated @RequestBody CmsCategoryBo bo) {
        return toAjax(iCmsCategoryService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 新增文章类型
     */
    @ApiOperation("新增自定义文章类型")
//    @PreAuthorize("@ss.hasAnyPermi('cms:article:add')" )
    @Log(title = "文章类型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/addCustom")
    public AjaxResult<Void> addCustom(@Validated @RequestBody CmsCategoryBo bo) {

        //判断分类父级是否开启自定义添加子分类
        if(bo.getParentId()!=null && bo.getParentId().compareTo(0L)==1){
            CmsCategory parent = iCmsCategoryService.getById(bo.getParentId());
            if(parent==null || parent.getCreateChildEnable().compareTo(0L)==0){
                return toAjax(0);
            }
            //查询模板分类
            CmsCategory temp = iCmsCategoryService.getById(parent.getChildTemplateId());
            if(temp==null){
                return toAjax(0);
            }
            bo.setCustomProperty(temp.getCustomProperty());
            bo.setCreateDataEnable(temp.getCreateDataEnable());
            bo.setCreateChildEnable(temp.getCreateChildEnable());
        }

        return toAjax(iCmsCategoryService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改内容分类
     */
    @ApiOperation("修改内容分类")
    @Log(title = "内容分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody CmsCategoryBo bo) {
        return toAjax(iCmsCategoryService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除内容分类
     */
    @ApiOperation("删除内容分类")
    @Log(title = "内容分类" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                   @PathVariable Long[] ids) {
        return toAjax(iCmsCategoryService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 获取文章props
     */
    @GetMapping("/getProps")
    public AjaxResult getProps() {
        String noFields = ",serialVersionUID,id,createTime,isDeleted,categoryId,categoryPath,status,weight,";
        LinkedHashMap<String, String> map = new LinkedHashMap();
        Field[] fields = CmsArticle.class.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            // 除过fieldMap中的属性，其他属性都获取
            if (!noFields.contains(","+fields[i].getName()+",")) {
                // Field field=clazz.getDeclaredField(fields[i].getName());
                boolean annotationPresent = fields[i].isAnnotationPresent(Excel.class);
                if (annotationPresent) {
                    // 获取注解值
                    String name = fields[i].getAnnotation(Excel.class).name();
                    map.put(fields[i].getName(),name);
                }
            }
        }
        return AjaxResult.success(map);
    }
}
