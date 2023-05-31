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
import com.yibei.yb.domain.vo.YbExpandReadingVo;
import com.yibei.yb.domain.bo.YbExpandReadingBo;
import com.yibei.yb.service.IYbExpandReadingService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 拓展阅读Controller
 *
 * @author yibei
 * @date 2022-05-11
 */
@Validated
@Api(value = "拓展阅读控制器", tags = {"拓展阅读管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybExpandReading")
public class YbExpandReadingController extends BaseController {

    private final IYbExpandReadingService iYbExpandReadingService;

    /**
     * 查询拓展阅读列表
     */
    @ApiOperation("查询拓展阅读列表")
    @PreAuthorize("@ss.hasPermi('yb:ybExpandReading:list')")
    @GetMapping("/list")
    public TableDataInfo<YbExpandReadingVo> list(@Validated(QueryGroup.class) YbExpandReadingBo bo) {
        TableDataInfo<YbExpandReadingVo> tableDataInfo = iYbExpandReadingService.queryPageList(bo);
        //数据联查操作
        iYbExpandReadingService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出拓展阅读列表
     */
    @ApiOperation("导出拓展阅读列表")
    @PreAuthorize("@ss.hasPermi('yb:ybExpandReading:export')")
    @Log(title = "拓展阅读", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbExpandReadingBo bo, HttpServletResponse response) {
        List<YbExpandReadingVo> list = iYbExpandReadingService.queryList(bo);
        ExcelUtil.exportExcel(list, "拓展阅读", YbExpandReadingVo.class, response);
    }

    /**
     * 获取拓展阅读详细信息
     */
    @ApiOperation("获取拓展阅读详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybExpandReading:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbExpandReadingVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbExpandReadingVo vo = iYbExpandReadingService.queryById(id);
        //vo = iYbExpandReadingService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增拓展阅读
     */
    @ApiOperation("新增拓展阅读")
    @PreAuthorize("@ss.hasPermi('yb:ybExpandReading:add')")
    @Log(title = "拓展阅读", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbExpandReadingBo bo) {
        return toAjax(iYbExpandReadingService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改拓展阅读
     */
    @ApiOperation("修改拓展阅读")
    @PreAuthorize("@ss.hasPermi('yb:ybExpandReading:edit')")
    @Log(title = "拓展阅读", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbExpandReadingBo bo) {
        return toAjax(iYbExpandReadingService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除拓展阅读
     */
    @ApiOperation("删除拓展阅读")
    @PreAuthorize("@ss.hasPermi('yb:ybExpandReading:remove')")
    @Log(title = "拓展阅读" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbExpandReadingService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
