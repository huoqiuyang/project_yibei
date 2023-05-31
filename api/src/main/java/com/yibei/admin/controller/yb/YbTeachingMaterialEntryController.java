package com.yibei.admin.controller.yb;

import java.util.List;
import java.util.Arrays;

import com.yibei.yb.domain.clientVo.YbTeachingMaterialEntryTitleVo;
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
import com.yibei.yb.domain.vo.YbTeachingMaterialEntryVo;
import com.yibei.yb.domain.bo.YbTeachingMaterialEntryBo;
import com.yibei.yb.service.IYbTeachingMaterialEntryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 教材词条Controller
 *
 * @author yibei
 * @date 2022-05-14
 */
@Validated
@Api(value = "教材词条控制器", tags = {"教材词条管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybTeachingMaterialEntry")
public class YbTeachingMaterialEntryController extends BaseController {

    private final IYbTeachingMaterialEntryService iYbTeachingMaterialEntryService;

    /**
     * 查询教材词条列表
     */
    @ApiOperation("查询教材词条列表")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialEntry:list')")
    @GetMapping("/list")
    public AjaxResult<List<YbTeachingMaterialEntryVo>> list(@Validated(QueryGroup.class) YbTeachingMaterialEntryBo bo) {
        List<YbTeachingMaterialEntryVo> list = iYbTeachingMaterialEntryService.queryList(bo);
        return AjaxResult.success(list);
    }

    @ApiOperation("查询教材词条列表标题")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialEntry:list')")
    @GetMapping("/selectList")
    public AjaxResult<List<YbTeachingMaterialEntryTitleVo>> selectList(@Validated(QueryGroup.class) YbTeachingMaterialEntryBo bo) {
        List<YbTeachingMaterialEntryTitleVo> list = iYbTeachingMaterialEntryService.selectList(bo);
        return AjaxResult.success(list);
    }

    /**
     * 导出教材词条列表
     */
    @ApiOperation("导出教材词条列表")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialEntry:export')")
    @Log(title = "教材词条", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbTeachingMaterialEntryBo bo, HttpServletResponse response) {
        List<YbTeachingMaterialEntryVo> list = iYbTeachingMaterialEntryService.queryList(bo);
        ExcelUtil.exportExcel(list, "教材词条", YbTeachingMaterialEntryVo.class, response);
    }

    /**
     * 获取教材词条详细信息
     */
    @ApiOperation("获取教材词条详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialEntry:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbTeachingMaterialEntryVo> getInfo(@NotNull(message = "主键不能为空")
                                                         @PathVariable("id") Long id) {
        YbTeachingMaterialEntryVo vo = iYbTeachingMaterialEntryService.queryById(id);
        //vo = iYbTeachingMaterialEntryService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增教材词条
     */
    @ApiOperation("新增教材词条")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialEntry:add')")
    @Log(title = "教材词条", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbTeachingMaterialEntryBo bo) {
        return toAjax(iYbTeachingMaterialEntryService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改教材词条
     */
    @ApiOperation("修改教材词条")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialEntry:edit')")
    @Log(title = "教材词条", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbTeachingMaterialEntryBo bo) {
        return toAjax(iYbTeachingMaterialEntryService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除教材词条
     */
    @ApiOperation("删除教材词条")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialEntry:remove')")
    @Log(title = "教材词条" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                   @PathVariable Long[] ids) {
        return toAjax(iYbTeachingMaterialEntryService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}