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
import com.yibei.yb.domain.vo.VLinkVo;
import com.yibei.yb.domain.bo.VLinkBo;
import com.yibei.yb.service.IVLinkService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 相关链接Controller
 *
 * @author yibei
 * @date 2022-06-09
 */
@Validated
@Api(value = "相关链接控制器", tags = {"相关链接管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/vLink")
public class VLinkController extends BaseController {

    private final IVLinkService iVLinkService;

    /**
     * 查询相关链接列表
     */
    @ApiOperation("查询相关链接列表")
    @PreAuthorize("@ss.hasPermi('yb:vLink:list')")
    @GetMapping("/list")
    public TableDataInfo<VLinkVo> list(@Validated(QueryGroup.class) VLinkBo bo) {
        TableDataInfo<VLinkVo> tableDataInfo = iVLinkService.queryPageList(bo);
        //数据联查操作
        iVLinkService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出相关链接列表
     */
    @ApiOperation("导出相关链接列表")
    @PreAuthorize("@ss.hasPermi('yb:vLink:export')")
    @Log(title = "相关链接", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated VLinkBo bo, HttpServletResponse response) {
        List<VLinkVo> list = iVLinkService.queryList(bo);
        ExcelUtil.exportExcel(list, "相关链接", VLinkVo.class, response);
    }

    /**
     * 获取相关链接详细信息
     */
    @ApiOperation("获取相关链接详细信息")
    @PreAuthorize("@ss.hasPermi('yb:vLink:query')")
    @GetMapping("/{type}")
    public AjaxResult<VLinkVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("type") String type) {
        VLinkVo vo = iVLinkService.queryById(type);
        //vo = iVLinkService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增相关链接
     */
    @ApiOperation("新增相关链接")
    @PreAuthorize("@ss.hasPermi('yb:vLink:add')")
    @Log(title = "相关链接", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody VLinkBo bo) {
        return toAjax(iVLinkService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改相关链接
     */
    @ApiOperation("修改相关链接")
    @PreAuthorize("@ss.hasPermi('yb:vLink:edit')")
    @Log(title = "相关链接", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody VLinkBo bo) {
        return toAjax(iVLinkService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除相关链接
     */
    @ApiOperation("删除相关链接")
    @PreAuthorize("@ss.hasPermi('yb:vLink:remove')")
    @Log(title = "相关链接" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{types}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable String[] types) {
        return toAjax(iVLinkService.deleteWithValidByIds(Arrays.asList(types), true) ? 1 : 0);
    }
}
