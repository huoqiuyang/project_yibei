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
import com.yibei.yb.domain.vo.YbAnswerLogVo;
import com.yibei.yb.domain.bo.YbAnswerLogBo;
import com.yibei.yb.service.IYbAnswerLogService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 答题记录Controller
 *
 * @author yibei
 * @date 2022-05-07
 */
@Validated
@Api(value = "答题记录控制器", tags = {"答题记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybAnswerLog")
public class YbAnswerLogController extends BaseController {

    private final IYbAnswerLogService iYbAnswerLogService;

    /**
     * 查询答题记录列表
     */
    @ApiOperation("查询答题记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybAnswerLog:list')")
    @GetMapping("/list")
    public TableDataInfo<YbAnswerLogVo> list(@Validated(QueryGroup.class) YbAnswerLogBo bo) {
        TableDataInfo<YbAnswerLogVo> tableDataInfo = iYbAnswerLogService.queryPageList(bo);
        //数据联查操作
        iYbAnswerLogService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出答题记录列表
     */
    @ApiOperation("导出答题记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybAnswerLog:export')")
    @Log(title = "答题记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbAnswerLogBo bo, HttpServletResponse response) {
        List<YbAnswerLogVo> list = iYbAnswerLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "答题记录", YbAnswerLogVo.class, response);
    }

    /**
     * 获取答题记录详细信息
     */
    @ApiOperation("获取答题记录详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybAnswerLog:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbAnswerLogVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbAnswerLogVo vo = iYbAnswerLogService.queryById(id);
        //vo = iYbAnswerLogService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增答题记录
     */
    @ApiOperation("新增答题记录")
    @PreAuthorize("@ss.hasPermi('yb:ybAnswerLog:add')")
    @Log(title = "答题记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbAnswerLogBo bo) {
        return toAjax(iYbAnswerLogService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改答题记录
     */
    @ApiOperation("修改答题记录")
    @PreAuthorize("@ss.hasPermi('yb:ybAnswerLog:edit')")
    @Log(title = "答题记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbAnswerLogBo bo) {
        return toAjax(iYbAnswerLogService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除答题记录
     */
    @ApiOperation("删除答题记录")
    @PreAuthorize("@ss.hasPermi('yb:ybAnswerLog:remove')")
    @Log(title = "答题记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbAnswerLogService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
