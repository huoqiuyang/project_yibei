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
import com.yibei.yb.domain.vo.YbUserVipVo;
import com.yibei.yb.domain.bo.YbUserVipBo;
import com.yibei.yb.service.IYbUserVipService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 用户会员Controller
 *
 * @author yibei
 * @date 2022-04-27
 */
@Validated
@Api(value = "用户会员控制器", tags = {"用户会员管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybUserVip")
public class YbUserVipController extends BaseController {

    private final IYbUserVipService iYbUserVipService;

    /**
     * 查询用户会员列表
     */
    @ApiOperation("查询用户会员列表")
    @PreAuthorize("@ss.hasPermi('yb:ybUserVip:list')")
    @GetMapping("/list")
    public TableDataInfo<YbUserVipVo> list(@Validated(QueryGroup.class) YbUserVipBo bo) {
        TableDataInfo<YbUserVipVo> tableDataInfo = iYbUserVipService.queryPageList(bo);
        //数据联查操作
        iYbUserVipService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出用户会员列表
     */
    @ApiOperation("导出用户会员列表")
    @PreAuthorize("@ss.hasPermi('yb:ybUserVip:export')")
    @Log(title = "用户会员", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbUserVipBo bo, HttpServletResponse response) {
        List<YbUserVipVo> list = iYbUserVipService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户会员", YbUserVipVo.class, response);
    }

    /**
     * 获取用户会员详细信息
     */
    @ApiOperation("获取用户会员详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybUserVip:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbUserVipVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbUserVipVo vo = iYbUserVipService.queryById(id);
        vo = iYbUserVipService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增用户会员
     */
    @ApiOperation("新增用户会员")
    @PreAuthorize("@ss.hasPermi('yb:ybUserVip:add')")
    @Log(title = "用户会员", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbUserVipBo bo) {
        return toAjax(iYbUserVipService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改用户会员
     */
    @ApiOperation("修改用户会员")
    @PreAuthorize("@ss.hasPermi('yb:ybUserVip:edit')")
    @Log(title = "用户会员", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbUserVipBo bo) {
        return toAjax(iYbUserVipService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除用户会员
     */
    @ApiOperation("删除用户会员")
    @PreAuthorize("@ss.hasPermi('yb:ybUserVip:remove')")
    @Log(title = "用户会员" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbUserVipService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
