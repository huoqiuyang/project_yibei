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
import com.yibei.yb.domain.vo.YbUserBookshelfVo;
import com.yibei.yb.domain.bo.YbUserBookshelfBo;
import com.yibei.yb.service.IYbUserBookshelfService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户书架Controller
 *
 * @author yibei
 * @date 2022-05-06
 */
@Validated
@Api(value = "用户书架控制器", tags = {"用户书架管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybUserBookshelf")
public class YbUserBookshelfController extends BaseController {

    private final IYbUserBookshelfService iYbUserBookshelfService;

    /**
     * 查询用户书架列表
     */
    @ApiOperation("查询用户书架列表")
    @PreAuthorize("@ss.hasPermi('yb:ybUserBookshelf:list')")
    @GetMapping("/list")
    public TableDataInfo<YbUserBookshelfVo> list(@Validated(QueryGroup.class) YbUserBookshelfBo bo) {
        TableDataInfo<YbUserBookshelfVo> tableDataInfo = iYbUserBookshelfService.queryPageList(bo);
        //数据联查操作
        iYbUserBookshelfService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出用户书架列表
     */
    @ApiOperation("导出用户书架列表")
    @PreAuthorize("@ss.hasPermi('yb:ybUserBookshelf:export')")
    @Log(title = "用户书架", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbUserBookshelfBo bo, HttpServletResponse response) {
        List<YbUserBookshelfVo> list = iYbUserBookshelfService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户书架", YbUserBookshelfVo.class, response);
    }

    /**
     * 获取用户书架详细信息
     */
    @ApiOperation("获取用户书架详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybUserBookshelf:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbUserBookshelfVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbUserBookshelfVo vo = iYbUserBookshelfService.queryById(id);
        //vo = iYbUserBookshelfService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增用户书架
     */
    @ApiOperation("新增用户书架")
    @PreAuthorize("@ss.hasPermi('yb:ybUserBookshelf:add')")
    @Log(title = "用户书架", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbUserBookshelfBo bo) {
        return toAjax(iYbUserBookshelfService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改用户书架
     */
    @ApiOperation("修改用户书架")
    @PreAuthorize("@ss.hasPermi('yb:ybUserBookshelf:edit')")
    @Log(title = "用户书架", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbUserBookshelfBo bo) {
        return toAjax(iYbUserBookshelfService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除用户书架
     */
    @ApiOperation("删除用户书架")
    @PreAuthorize("@ss.hasPermi('yb:ybUserBookshelf:remove')")
    @Log(title = "用户书架" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbUserBookshelfService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
