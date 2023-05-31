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
import com.yibei.yb.domain.vo.YbNoteVo;
import com.yibei.yb.domain.bo.YbNoteBo;
import com.yibei.yb.service.IYbNoteService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 笔记Controller
 *
 * @author yibei
 * @date 2022-05-11
 */
@Validated
@Api(value = "笔记控制器", tags = {"笔记管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybNote")
public class YbNoteController extends BaseController {

    private final IYbNoteService iYbNoteService;

    /**
     * 查询笔记列表
     */
    @ApiOperation("查询笔记列表")
    @PreAuthorize("@ss.hasPermi('yb:ybNote:list')")
    @GetMapping("/list")
    public TableDataInfo<YbNoteVo> list(@Validated(QueryGroup.class) YbNoteBo bo) {
        TableDataInfo<YbNoteVo> tableDataInfo = iYbNoteService.queryPageList(bo);
        //数据联查操作
        iYbNoteService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出笔记列表
     */
    @ApiOperation("导出笔记列表")
    @PreAuthorize("@ss.hasPermi('yb:ybNote:export')")
    @Log(title = "笔记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbNoteBo bo, HttpServletResponse response) {
        List<YbNoteVo> list = iYbNoteService.queryList(bo);
        ExcelUtil.exportExcel(list, "笔记", YbNoteVo.class, response);
    }

    /**
     * 获取笔记详细信息
     */
    @ApiOperation("获取笔记详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybNote:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbNoteVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbNoteVo vo = iYbNoteService.queryById(id);
        //vo = iYbNoteService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增笔记
     */
    @ApiOperation("新增笔记")
    @PreAuthorize("@ss.hasPermi('yb:ybNote:add')")
    @Log(title = "笔记", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbNoteBo bo) {
        return toAjax(iYbNoteService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改笔记
     */
    @ApiOperation("修改笔记")
    @PreAuthorize("@ss.hasPermi('yb:ybNote:edit')")
    @Log(title = "笔记", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbNoteBo bo) {
        return toAjax(iYbNoteService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除笔记
     */
    @ApiOperation("删除笔记")
    @PreAuthorize("@ss.hasPermi('yb:ybNote:remove')")
    @Log(title = "笔记" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbNoteService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
