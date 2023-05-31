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
import com.yibei.yb.domain.vo.YbUpvoteLogVo;
import com.yibei.yb.domain.bo.YbUpvoteLogBo;
import com.yibei.yb.service.IYbUpvoteLogService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 点赞记录Controller
 *
 * @author yibei
 * @date 2022-05-19
 */
@Validated
@Api(value = "点赞记录控制器", tags = {"点赞记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybUpvoteLog")
public class YbUpvoteLogController extends BaseController {

    private final IYbUpvoteLogService iYbUpvoteLogService;

    /**
     * 查询点赞记录列表
     */
    @ApiOperation("查询点赞记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybUpvoteLog:list')")
    @GetMapping("/list")
    public TableDataInfo<YbUpvoteLogVo> list(@Validated(QueryGroup.class) YbUpvoteLogBo bo) {
        TableDataInfo<YbUpvoteLogVo> tableDataInfo = iYbUpvoteLogService.queryPageList(bo);
        //数据联查操作
        iYbUpvoteLogService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出点赞记录列表
     */
    @ApiOperation("导出点赞记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybUpvoteLog:export')")
    @Log(title = "点赞记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbUpvoteLogBo bo, HttpServletResponse response) {
        List<YbUpvoteLogVo> list = iYbUpvoteLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "点赞记录", YbUpvoteLogVo.class, response);
    }

    /**
     * 获取点赞记录详细信息
     */
    @ApiOperation("获取点赞记录详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybUpvoteLog:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbUpvoteLogVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbUpvoteLogVo vo = iYbUpvoteLogService.queryById(id);
        //vo = iYbUpvoteLogService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增点赞记录
     */
    @ApiOperation("新增点赞记录")
    @PreAuthorize("@ss.hasPermi('yb:ybUpvoteLog:add')")
    @Log(title = "点赞记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbUpvoteLogBo bo) {
        return toAjax(iYbUpvoteLogService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改点赞记录
     */
    @ApiOperation("修改点赞记录")
    @PreAuthorize("@ss.hasPermi('yb:ybUpvoteLog:edit')")
    @Log(title = "点赞记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbUpvoteLogBo bo) {
        return toAjax(iYbUpvoteLogService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除点赞记录
     */
    @ApiOperation("删除点赞记录")
    @PreAuthorize("@ss.hasPermi('yb:ybUpvoteLog:remove')")
    @Log(title = "点赞记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbUpvoteLogService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
