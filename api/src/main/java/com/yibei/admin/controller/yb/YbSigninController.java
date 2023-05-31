package com.yibei.admin.controller.yb;;

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
import com.yibei.yb.domain.vo.YbSigninVo;
import com.yibei.yb.domain.bo.YbSigninBo;
import com.yibei.yb.service.IYbSigninService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 签到Controller
 *
 * @author yibei
 * @date 2022-05-18
 */
@Validated
@Api(value = "签到控制器", tags = {"签到管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybSignin")
public class YbSigninController extends BaseController {

    private final IYbSigninService iYbSigninService;

    /**
     * 查询签到列表
     */
    @ApiOperation("查询签到列表")
    @PreAuthorize("@ss.hasPermi('yb:ybSignin:list')")
    @GetMapping("/list")
    public TableDataInfo<YbSigninVo> list(@Validated(QueryGroup.class) YbSigninBo bo) {
        TableDataInfo<YbSigninVo> tableDataInfo = iYbSigninService.queryPageList(bo);
        //数据联查操作
        iYbSigninService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出签到列表
     */
    @ApiOperation("导出签到列表")
    @PreAuthorize("@ss.hasPermi('yb:ybSignin:export')")
    @Log(title = "签到", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbSigninBo bo, HttpServletResponse response) {
        List<YbSigninVo> list = iYbSigninService.queryList(bo);
        ExcelUtil.exportExcel(list, "签到", YbSigninVo.class, response);
    }

    /**
     * 获取签到详细信息
     */
    @ApiOperation("获取签到详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybSignin:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbSigninVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbSigninVo vo = iYbSigninService.queryById(id);
        //vo = iYbSigninService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增签到
     */
    @ApiOperation("新增签到")
    @PreAuthorize("@ss.hasPermi('yb:ybSignin:add')")
    @Log(title = "签到", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbSigninBo bo) {
        return toAjax(iYbSigninService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改签到
     */
    @ApiOperation("修改签到")
    @PreAuthorize("@ss.hasPermi('yb:ybSignin:edit')")
    @Log(title = "签到", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbSigninBo bo) {
        return toAjax(iYbSigninService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除签到
     */
    @ApiOperation("删除签到")
    @PreAuthorize("@ss.hasPermi('yb:ybSignin:remove')")
    @Log(title = "签到" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbSigninService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
