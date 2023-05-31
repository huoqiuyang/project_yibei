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
import com.yibei.yb.domain.vo.YbStudyTimeLogVo;
import com.yibei.yb.domain.bo.YbStudyTimeLogBo;
import com.yibei.yb.service.IYbStudyTimeLogService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 学习时间记录Controller
 *
 * @author yibei
 * @date 2022-05-18
 */
@Validated
@Api(value = "学习时间记录控制器", tags = {"学习时间记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybStudyTimeLog")
public class YbStudyTimeLogController extends BaseController {

    private final IYbStudyTimeLogService iYbStudyTimeLogService;

    /**
     * 查询学习时间记录列表
     */
    @ApiOperation("查询学习时间记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyTimeLog:list')")
    @GetMapping("/list")
    public TableDataInfo<YbStudyTimeLogVo> list(@Validated(QueryGroup.class) YbStudyTimeLogBo bo) {
        TableDataInfo<YbStudyTimeLogVo> tableDataInfo = iYbStudyTimeLogService.queryPageList(bo);
        //数据联查操作
        iYbStudyTimeLogService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出学习时间记录列表
     */
    @ApiOperation("导出学习时间记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyTimeLog:export')")
    @Log(title = "学习时间记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbStudyTimeLogBo bo, HttpServletResponse response) {
        List<YbStudyTimeLogVo> list = iYbStudyTimeLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "学习时间记录", YbStudyTimeLogVo.class, response);
    }

    /**
     * 获取学习时间记录详细信息
     */
    @ApiOperation("获取学习时间记录详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyTimeLog:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbStudyTimeLogVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbStudyTimeLogVo vo = iYbStudyTimeLogService.queryById(id);
        //vo = iYbStudyTimeLogService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增学习时间记录
     */
    @ApiOperation("新增学习时间记录")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyTimeLog:add')")
    @Log(title = "学习时间记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbStudyTimeLogBo bo) {
        return toAjax(iYbStudyTimeLogService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改学习时间记录
     */
    @ApiOperation("修改学习时间记录")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyTimeLog:edit')")
    @Log(title = "学习时间记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbStudyTimeLogBo bo) {
        return toAjax(iYbStudyTimeLogService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除学习时间记录
     */
    @ApiOperation("删除学习时间记录")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyTimeLog:remove')")
    @Log(title = "学习时间记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbStudyTimeLogService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
