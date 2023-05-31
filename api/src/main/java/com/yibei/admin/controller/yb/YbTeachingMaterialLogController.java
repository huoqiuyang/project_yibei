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
import com.yibei.yb.domain.vo.YbTeachingMaterialLogVo;
import com.yibei.yb.domain.bo.YbTeachingMaterialLogBo;
import com.yibei.yb.service.IYbTeachingMaterialLogService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 教材阅读背诵记录Controller
 *
 * @author yibei
 * @date 2022-05-12
 */
@Validated
@Api(value = "教材阅读背诵记录控制器", tags = {"教材阅读背诵记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybTeachingMaterialLog")
public class YbTeachingMaterialLogController extends BaseController {

    private final IYbTeachingMaterialLogService iYbTeachingMaterialLogService;

    /**
     * 查询教材阅读背诵记录列表
     */
    @ApiOperation("查询教材阅读背诵记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialLog:list')")
    @GetMapping("/list")
    public TableDataInfo<YbTeachingMaterialLogVo> list(@Validated(QueryGroup.class) YbTeachingMaterialLogBo bo) {
        TableDataInfo<YbTeachingMaterialLogVo> tableDataInfo = iYbTeachingMaterialLogService.queryPageList(bo);
        //数据联查操作
        iYbTeachingMaterialLogService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出教材阅读背诵记录列表
     */
    @ApiOperation("导出教材阅读背诵记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialLog:export')")
    @Log(title = "教材阅读背诵记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbTeachingMaterialLogBo bo, HttpServletResponse response) {
        List<YbTeachingMaterialLogVo> list = iYbTeachingMaterialLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "教材阅读背诵记录", YbTeachingMaterialLogVo.class, response);
    }

    /**
     * 获取教材阅读背诵记录详细信息
     */
    @ApiOperation("获取教材阅读背诵记录详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialLog:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbTeachingMaterialLogVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbTeachingMaterialLogVo vo = iYbTeachingMaterialLogService.queryById(id);
        //vo = iYbTeachingMaterialLogService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增教材阅读背诵记录
     */
    @ApiOperation("新增教材阅读背诵记录")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialLog:add')")
    @Log(title = "教材阅读背诵记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbTeachingMaterialLogBo bo) {
        return toAjax(iYbTeachingMaterialLogService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改教材阅读背诵记录
     */
    @ApiOperation("修改教材阅读背诵记录")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialLog:edit')")
    @Log(title = "教材阅读背诵记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbTeachingMaterialLogBo bo) {
        return toAjax(iYbTeachingMaterialLogService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除教材阅读背诵记录
     */
    @ApiOperation("删除教材阅读背诵记录")
    @PreAuthorize("@ss.hasPermi('yb:ybTeachingMaterialLog:remove')")
    @Log(title = "教材阅读背诵记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbTeachingMaterialLogService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
