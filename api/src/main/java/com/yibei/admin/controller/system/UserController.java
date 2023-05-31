package com.yibei.admin.controller.system;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.common.utils.PageUtils;
import com.yibei.yb.domain.clientVo.YbOrderInfoVo;
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
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.domain.bo.UserBo;
import com.yibei.system.service.IUserService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户Controller
 *
 * @author yibei
 * @date 2022-04-26
 */
@Validated
@Api(value = "用户控制器", tags = {"用户管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/system/clientUser")
public class UserController extends BaseController {

    private final IUserService iUserService;

    /**
     * 查询用户列表
     */
    /*@ApiOperation("查询用户列表")
    @PreAuthorize("@ss.hasPermi('system:clientUser:list')")
    @GetMapping("/list")
    public TableDataInfo<UserVo> list(@Validated(QueryGroup.class) UserBo bo) {
        TableDataInfo<UserVo> tableDataInfo = iUserService.queryPageList(bo);
        //数据联查操作
        iUserService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }*/

    /**
     * 查询用户列表
     */
    @ApiOperation("查询用户列表")
    @PreAuthorize("@ss.hasPermi('system:clientUser:list')")
    @GetMapping("/list")
    public TableDataInfo<UserVo> list(@Validated(QueryGroup.class) UserBo bo) {
        Page<UserVo> page = iUserService.getList(bo);
        return PageUtils.buildDataInfo(page.getRecords(),page.getTotal());
    }

    /**
     * 导出用户列表
     */
    /*@ApiOperation("导出用户列表")
    @PreAuthorize("@ss.hasPermi('system:clientUser:export')")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated UserBo bo, HttpServletResponse response) {
        List<UserVo> list = iUserService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户", UserVo.class, response);
    }*/

    /**
     * 导出用户列表
     */
    @ApiOperation("导出用户列表")
    @PreAuthorize("@ss.hasPermi('system:clientUser:export')")
    @Log(title = "用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated UserBo bo, HttpServletResponse response) {
        bo.setPageSize(999999);
        Page<UserVo> page = iUserService.getList(bo);
        ExcelUtil.exportExcel(page.getRecords(), "用户", UserVo.class, response);
    }

    /**
     * 获取用户详细信息
     */
    @ApiOperation("获取用户详细信息")
    @PreAuthorize("@ss.hasPermi('system:clientUser:query')")
    @GetMapping("/{id}")
    public AjaxResult<UserVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        UserVo vo = iUserService.queryById(id);
        //vo = iUserService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增用户
     */
    @ApiOperation("新增用户")
    @PreAuthorize("@ss.hasPermi('system:clientUser:add')")
    @Log(title = "用户", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody UserBo bo) {
        return toAjax(iUserService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改用户
     */
    @ApiOperation("修改用户")
    @PreAuthorize("@ss.hasPermi('system:clientUser:edit')")
    @Log(title = "用户", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody UserBo bo) {
        return toAjax(iUserService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除用户
     */
    @ApiOperation("删除用户")
    @PreAuthorize("@ss.hasPermi('system:clientUser:remove')")
    @Log(title = "用户" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iUserService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
