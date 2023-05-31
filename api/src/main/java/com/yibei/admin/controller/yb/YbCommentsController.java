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
import com.yibei.yb.domain.vo.YbCommentsVo;
import com.yibei.yb.domain.bo.YbCommentsBo;
import com.yibei.yb.service.IYbCommentsService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 评论Controller
 *
 * @author yibei
 * @date 2022-05-19
 */
@Validated
@Api(value = "评论控制器", tags = {"评论管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybComments")
public class YbCommentsController extends BaseController {

    private final IYbCommentsService iYbCommentsService;

    /**
     * 查询评论列表
     */
    @ApiOperation("查询评论列表")
    @PreAuthorize("@ss.hasPermi('yb:ybComments:list')")
    @GetMapping("/list")
    public TableDataInfo<YbCommentsVo> list(@Validated(QueryGroup.class) YbCommentsBo bo) {
        TableDataInfo<YbCommentsVo> tableDataInfo = iYbCommentsService.queryPageList(bo);
        //数据联查操作
        iYbCommentsService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出评论列表
     */
    @ApiOperation("导出评论列表")
    @PreAuthorize("@ss.hasPermi('yb:ybComments:export')")
    @Log(title = "评论", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbCommentsBo bo, HttpServletResponse response) {
        List<YbCommentsVo> list = iYbCommentsService.queryList(bo);
        ExcelUtil.exportExcel(list, "评论", YbCommentsVo.class, response);
    }

    /**
     * 获取评论详细信息
     */
    @ApiOperation("获取评论详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybComments:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbCommentsVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbCommentsVo vo = iYbCommentsService.queryById(id);
        //vo = iYbCommentsService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增评论
     */
    @ApiOperation("新增评论")
    @PreAuthorize("@ss.hasPermi('yb:ybComments:add')")
    @Log(title = "评论", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbCommentsBo bo) {
        return toAjax(iYbCommentsService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改评论
     */
    @ApiOperation("修改评论")
    @PreAuthorize("@ss.hasPermi('yb:ybComments:edit')")
    @Log(title = "评论", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbCommentsBo bo) {
        return toAjax(iYbCommentsService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除评论
     */
    @ApiOperation("删除评论")
    @PreAuthorize("@ss.hasPermi('yb:ybComments:remove')")
    @Log(title = "评论" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbCommentsService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
