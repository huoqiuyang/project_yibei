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
import com.yibei.yb.domain.vo.YbUserCollectionVo;
import com.yibei.yb.domain.bo.YbUserCollectionBo;
import com.yibei.yb.service.IYbUserCollectionService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户收藏Controller
 *
 * @author yibei
 * @date 2022-05-06
 */
@Validated
@Api(value = "用户收藏控制器", tags = {"用户收藏管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybUserCollection")
public class YbUserCollectionController extends BaseController {

    private final IYbUserCollectionService iYbUserCollectionService;

    /**
     * 查询用户收藏列表
     */
    @ApiOperation("查询用户收藏列表")
    @PreAuthorize("@ss.hasPermi('yb:ybUserCollection:list')")
    @GetMapping("/list")
    public TableDataInfo<YbUserCollectionVo> list(@Validated(QueryGroup.class) YbUserCollectionBo bo) {
        TableDataInfo<YbUserCollectionVo> tableDataInfo = iYbUserCollectionService.queryPageList(bo);
        //数据联查操作
        iYbUserCollectionService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出用户收藏列表
     */
    @ApiOperation("导出用户收藏列表")
    @PreAuthorize("@ss.hasPermi('yb:ybUserCollection:export')")
    @Log(title = "用户收藏", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbUserCollectionBo bo, HttpServletResponse response) {
        List<YbUserCollectionVo> list = iYbUserCollectionService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户收藏", YbUserCollectionVo.class, response);
    }

    /**
     * 获取用户收藏详细信息
     */
    @ApiOperation("获取用户收藏详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybUserCollection:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbUserCollectionVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbUserCollectionVo vo = iYbUserCollectionService.queryById(id);
        //vo = iYbUserCollectionService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增用户收藏
     */
    @ApiOperation("新增用户收藏")
    @PreAuthorize("@ss.hasPermi('yb:ybUserCollection:add')")
    @Log(title = "用户收藏", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbUserCollectionBo bo) {
        return toAjax(iYbUserCollectionService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改用户收藏
     */
    @ApiOperation("修改用户收藏")
    @PreAuthorize("@ss.hasPermi('yb:ybUserCollection:edit')")
    @Log(title = "用户收藏", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbUserCollectionBo bo) {
        return toAjax(iYbUserCollectionService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除用户收藏
     */
    @ApiOperation("删除用户收藏")
    @PreAuthorize("@ss.hasPermi('yb:ybUserCollection:remove')")
    @Log(title = "用户收藏" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbUserCollectionService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
