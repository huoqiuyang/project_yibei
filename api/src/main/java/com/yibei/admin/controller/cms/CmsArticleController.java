package com.yibei.admin.controller.cms;

import java.util.List;
import java.util.Arrays;

import lombok.RequiredArgsConstructor;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.*;
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
import com.yibei.cms.domain.vo.CmsArticleVo;
import com.yibei.cms.domain.bo.CmsArticleBo;
import com.yibei.cms.service.ICmsArticleService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 文章Controller
 *
 * @author yibei
 * @date 2022-01-08
 */
@Validated
@Api(value = "文章控制器", tags = {"文章管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/cms/article")
public class CmsArticleController extends BaseController {

    private final ICmsArticleService iCmsArticleService;

    /**
     * 查询文章列表
     */
    @ApiOperation("查询文章列表")
    @GetMapping("/list")
    public TableDataInfo<CmsArticleVo> list(@Validated(QueryGroup.class) CmsArticleBo bo) {
        TableDataInfo<CmsArticleVo> tableDataInfo = iCmsArticleService.queryPageList(bo);
        //数据联查操作
        iCmsArticleService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出文章列表
     */
    @ApiOperation("导出文章列表")
    @Log(title = "文章", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated CmsArticleBo bo, HttpServletResponse response) {
        List<CmsArticleVo> list = iCmsArticleService.queryList(bo);
        ExcelUtil.exportExcel(list, "文章", CmsArticleVo.class, response);
    }

    /**
     * 获取文章详细信息
     */
    @ApiOperation("获取文章详细信息")
    @GetMapping("/{id}")
    public AjaxResult<CmsArticleVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        CmsArticleVo vo = iCmsArticleService.queryById(id);
        //vo = iCmsArticleService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增文章
     */
    @ApiOperation("新增文章")
    @Log(title = "文章", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody CmsArticleBo bo) {
        return toAjax(iCmsArticleService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改文章
     */
    @ApiOperation("修改文章")
    @Log(title = "文章", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody CmsArticleBo bo) {
        return toAjax(iCmsArticleService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除文章
     */
    @ApiOperation("删除文章")
    @Log(title = "文章" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iCmsArticleService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
