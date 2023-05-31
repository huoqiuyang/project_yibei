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
import com.yibei.yb.domain.vo.YbOrderVo;
import com.yibei.yb.domain.bo.YbOrderBo;
import com.yibei.yb.service.IYbOrderService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员订单Controller
 *
 * @author yibei
 * @date 2022-04-27
 */
@Validated
@Api(value = "会员订单控制器", tags = {"会员订单管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybOrder")
public class YbOrderController extends BaseController {

    private final IYbOrderService iYbOrderService;

    /**
     * 查询会员订单列表
     */
    @ApiOperation("查询会员订单列表")
    @PreAuthorize("@ss.hasPermi('yb:ybOrder:list')")
    @GetMapping("/list")
    public TableDataInfo<YbOrderVo> list(@Validated(QueryGroup.class) YbOrderBo bo) {
        TableDataInfo<YbOrderVo> tableDataInfo = iYbOrderService.queryPageList(bo);
        //数据联查操作
        iYbOrderService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出会员订单列表
     */
    @ApiOperation("导出会员订单列表")
    @PreAuthorize("@ss.hasPermi('yb:ybOrder:export')")
    @Log(title = "会员订单", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbOrderBo bo, HttpServletResponse response) {
        List<YbOrderVo> list = iYbOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员订单", YbOrderVo.class, response);
    }

    /**
     * 获取会员订单详细信息
     */
    @ApiOperation("获取会员订单详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybOrder:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbOrderVo vo = iYbOrderService.queryById(id);
        //vo = iYbOrderService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增会员订单
     */
    @ApiOperation("新增会员订单")
    @PreAuthorize("@ss.hasPermi('yb:ybOrder:add')")
    @Log(title = "会员订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbOrderBo bo) {
        return toAjax(iYbOrderService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改会员订单
     */
    @ApiOperation("修改会员订单")
    @PreAuthorize("@ss.hasPermi('yb:ybOrder:edit')")
    @Log(title = "会员订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbOrderBo bo) {
        return toAjax(iYbOrderService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除会员订单
     */
    @ApiOperation("删除会员订单")
    @PreAuthorize("@ss.hasPermi('yb:ybOrder:remove')")
    @Log(title = "会员订单" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbOrderService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
