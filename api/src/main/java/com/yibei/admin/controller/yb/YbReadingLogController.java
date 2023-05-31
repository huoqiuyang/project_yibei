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
import com.yibei.yb.domain.vo.YbReadingLogVo;
import com.yibei.yb.domain.bo.YbReadingLogBo;
import com.yibei.yb.service.IYbReadingLogService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 阅读记录Controller
 *
 * @author yibei
 * @date 2022-05-11
 */
@Validated
@Api(value = "阅读记录控制器", tags = {"阅读记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybReadingLog")
public class YbReadingLogController extends BaseController {

    private final IYbReadingLogService iYbReadingLogService;

    /**
     * 查询阅读记录列表
     */
    @ApiOperation("查询阅读记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybReadingLog:list')")
    @GetMapping("/list")
    public TableDataInfo<YbReadingLogVo> list(@Validated(QueryGroup.class) YbReadingLogBo bo) {
        TableDataInfo<YbReadingLogVo> tableDataInfo = iYbReadingLogService.queryPageList(bo);
        //数据联查操作
        iYbReadingLogService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出阅读记录列表
     */
    @ApiOperation("导出阅读记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybReadingLog:export')")
    @Log(title = "阅读记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbReadingLogBo bo, HttpServletResponse response) {
        List<YbReadingLogVo> list = iYbReadingLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "阅读记录", YbReadingLogVo.class, response);
    }

    /**
     * 获取阅读记录详细信息
     */
    @ApiOperation("获取阅读记录详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybReadingLog:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbReadingLogVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbReadingLogVo vo = iYbReadingLogService.queryById(id);
        //vo = iYbReadingLogService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增阅读记录
     */
    @ApiOperation("新增阅读记录")
    @PreAuthorize("@ss.hasPermi('yb:ybReadingLog:add')")
    @Log(title = "阅读记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbReadingLogBo bo) {
        return toAjax(iYbReadingLogService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改阅读记录
     */
    @ApiOperation("修改阅读记录")
    @PreAuthorize("@ss.hasPermi('yb:ybReadingLog:edit')")
    @Log(title = "阅读记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbReadingLogBo bo) {
        return toAjax(iYbReadingLogService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除阅读记录
     */
    @ApiOperation("删除阅读记录")
    @PreAuthorize("@ss.hasPermi('yb:ybReadingLog:remove')")
    @Log(title = "阅读记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbReadingLogService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
