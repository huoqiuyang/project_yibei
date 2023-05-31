package com.yibei.admin.controller.yb;

import java.util.List;
import java.util.Arrays;

import com.yibei.yb.domain.clientVo.YbQuestionBankContentTitleVo;
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
import com.yibei.yb.domain.vo.YbQuestionBankContentVo;
import com.yibei.yb.domain.bo.YbQuestionBankContentBo;
import com.yibei.yb.service.IYbQuestionBankContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 题库内容Controller
 *
 * @author yibei
 * @date 2022-05-06
 */
@Validated
@Api(value = "题库内容控制器", tags = {"题库内容管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybQuestionBankContent")
public class YbQuestionBankContentController extends BaseController {

    private final IYbQuestionBankContentService iYbQuestionBankContentService;

    /**
     * 查询题库内容列表
     */
    @ApiOperation("查询题库内容列表")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBankContent:list')")
    @GetMapping("/list")
    public AjaxResult<List<YbQuestionBankContentVo>> list(@Validated(QueryGroup.class) YbQuestionBankContentBo bo) {
        List<YbQuestionBankContentVo> list = iYbQuestionBankContentService.queryList(bo);
        return AjaxResult.success(list);
    }
    @ApiOperation("查询题库内容列表标题")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBankContent:list')")
    @GetMapping("/selectList")
    public AjaxResult<List<YbQuestionBankContentTitleVo>> selectList(@Validated(QueryGroup.class) YbQuestionBankContentBo bo) {
        List<YbQuestionBankContentTitleVo> list = iYbQuestionBankContentService.selectList(bo);
        return AjaxResult.success(list);
    }

    /**
     * 导出题库内容列表
     */
    @ApiOperation("导出题库内容列表")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBankContent:export')")
    @Log(title = "题库内容", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbQuestionBankContentBo bo, HttpServletResponse response) {
        List<YbQuestionBankContentVo> list = iYbQuestionBankContentService.queryList(bo);
        ExcelUtil.exportExcel(list, "题库内容", YbQuestionBankContentVo.class, response);
    }

    /**
     * 获取题库内容详细信息
     */
    @ApiOperation("获取题库内容详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBankContent:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbQuestionBankContentVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbQuestionBankContentVo vo = iYbQuestionBankContentService.queryById(id);
        //vo = iYbQuestionBankContentService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增题库内容
     */
    @ApiOperation("新增题库内容")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBankContent:add')")
    @Log(title = "题库内容", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbQuestionBankContentBo bo) {
        return toAjax(iYbQuestionBankContentService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改题库内容
     */
    @ApiOperation("修改题库内容")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBankContent:edit')")
    @Log(title = "题库内容", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbQuestionBankContentBo bo) {
        return toAjax(iYbQuestionBankContentService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除题库内容
     */
    @ApiOperation("删除题库内容")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBankContent:remove')")
    @Log(title = "题库内容" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbQuestionBankContentService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
