package com.yibei.admin.controller.yb;

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
import com.yibei.yb.domain.vo.YbTeachingMaterialVo;
import com.yibei.yb.domain.bo.YbTeachingMaterialBo;
import com.yibei.yb.service.IYbTeachingMaterialService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 教材Controller
 *
 * @author yibei
 * @date 2022-05-11
 */
@Validated
@Api(value = "教材控制器", tags = {"教材管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybTeachingMaterial")
public class YbTeachingMaterialController extends BaseController {

    private final IYbTeachingMaterialService iYbTeachingMaterialService;

    /**
     * 查询教材列表
     */
    @ApiOperation("查询教材列表")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterial:list')")
    @GetMapping("/list")
    public TableDataInfo<YbTeachingMaterialVo> list(@Validated(QueryGroup.class) YbTeachingMaterialBo bo) {
        TableDataInfo<YbTeachingMaterialVo> tableDataInfo = iYbTeachingMaterialService.queryPageList(bo);
        //数据联查操作
        iYbTeachingMaterialService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出教材列表
     */
    @ApiOperation("导出教材列表")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterial:export')")
    @Log(title = "教材", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbTeachingMaterialBo bo, HttpServletResponse response) {
        List<YbTeachingMaterialVo> list = iYbTeachingMaterialService.queryList(bo);
        ExcelUtil.exportExcel(list, "教材", YbTeachingMaterialVo.class, response);
    }

    /**
     * 获取教材详细信息
     */
    @ApiOperation("获取教材详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterial:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbTeachingMaterialVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbTeachingMaterialVo vo = iYbTeachingMaterialService.queryById(id);
        //vo = iYbTeachingMaterialService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增教材
     */
    @ApiOperation("新增教材")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterial:add')")
    @Log(title = "教材", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbTeachingMaterialBo bo) {
        return toAjax(iYbTeachingMaterialService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改教材
     */
    @ApiOperation("修改教材")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterial:edit')")
    @Log(title = "教材", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbTeachingMaterialBo bo) {
        return toAjax(iYbTeachingMaterialService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除教材
     */
    @ApiOperation("删除教材")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterial:remove')")
    @Log(title = "教材" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbTeachingMaterialService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
