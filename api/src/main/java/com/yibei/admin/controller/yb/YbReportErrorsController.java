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
import com.yibei.yb.domain.vo.YbReportErrorsVo;
import com.yibei.yb.domain.bo.YbReportErrorsBo;
import com.yibei.yb.service.IYbReportErrorsService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 报错Controller
 *
 * @author yibei
 * @date 2022-05-18
 */
@Validated
@Api(value = "报错控制器", tags = {"报错管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybReportErrors")
public class YbReportErrorsController extends BaseController {

    private final IYbReportErrorsService iYbReportErrorsService;

    /**
     * 查询报错列表
     */
    @ApiOperation("查询报错列表")
    @PreAuthorize("@ss.hasPermi('yb:ybReportErrors:list')")
    @GetMapping("/list")
    public TableDataInfo<YbReportErrorsVo> list(@Validated(QueryGroup.class) YbReportErrorsBo bo) {
        TableDataInfo<YbReportErrorsVo> tableDataInfo = iYbReportErrorsService.queryPageList(bo);
        //数据联查操作
        iYbReportErrorsService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出报错列表
     */
    @ApiOperation("导出报错列表")
    @PreAuthorize("@ss.hasPermi('yb:ybReportErrors:export')")
    @Log(title = "报错", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbReportErrorsBo bo, HttpServletResponse response) {
        List<YbReportErrorsVo> list = iYbReportErrorsService.queryList(bo);
        ExcelUtil.exportExcel(list, "报错", YbReportErrorsVo.class, response);
    }

    /**
     * 获取报错详细信息
     */
    @ApiOperation("获取报错详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybReportErrors:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbReportErrorsVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbReportErrorsVo vo = iYbReportErrorsService.queryById(id);
        //vo = iYbReportErrorsService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增报错
     */
    @ApiOperation("新增报错")
    @PreAuthorize("@ss.hasPermi('yb:ybReportErrors:add')")
    @Log(title = "报错", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbReportErrorsBo bo) {
        return toAjax(iYbReportErrorsService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改报错
     */
    @ApiOperation("修改报错")
    @PreAuthorize("@ss.hasPermi('yb:ybReportErrors:edit')")
    @Log(title = "报错", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbReportErrorsBo bo) {
        return toAjax(iYbReportErrorsService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除报错
     */
    @ApiOperation("删除报错")
    @PreAuthorize("@ss.hasPermi('yb:ybReportErrors:remove')")
    @Log(title = "报错" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbReportErrorsService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
