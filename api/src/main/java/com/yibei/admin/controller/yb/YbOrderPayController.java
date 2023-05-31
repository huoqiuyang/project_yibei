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
import com.yibei.yb.domain.vo.YbOrderPayVo;
import com.yibei.yb.domain.bo.YbOrderPayBo;
import com.yibei.yb.service.IYbOrderPayService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 支付记录Controller
 *
 * @author yibei
 * @date 2022-04-27
 */
@Validated
@Api(value = "支付记录控制器", tags = {"支付记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybOrderPay")
public class YbOrderPayController extends BaseController {

    private final IYbOrderPayService iYbOrderPayService;

    /**
     * 查询支付记录列表
     */
    @ApiOperation("查询支付记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybOrderPay:list')")
    @GetMapping("/list")
    public TableDataInfo<YbOrderPayVo> list(@Validated(QueryGroup.class) YbOrderPayBo bo) {
        TableDataInfo<YbOrderPayVo> tableDataInfo = iYbOrderPayService.queryPageList(bo);
        //数据联查操作
        iYbOrderPayService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出支付记录列表
     */
    @ApiOperation("导出支付记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybOrderPay:export')")
    @Log(title = "支付记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbOrderPayBo bo, HttpServletResponse response) {
        List<YbOrderPayVo> list = iYbOrderPayService.queryList(bo);
        ExcelUtil.exportExcel(list, "支付记录", YbOrderPayVo.class, response);
    }

    /**
     * 获取支付记录详细信息
     */
    @ApiOperation("获取支付记录详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybOrderPay:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbOrderPayVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbOrderPayVo vo = iYbOrderPayService.queryById(id);
        //vo = iYbOrderPayService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增支付记录
     */
    @ApiOperation("新增支付记录")
    @PreAuthorize("@ss.hasPermi('yb:ybOrderPay:add')")
    @Log(title = "支付记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbOrderPayBo bo) {
        return toAjax(iYbOrderPayService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改支付记录
     */
    @ApiOperation("修改支付记录")
    @PreAuthorize("@ss.hasPermi('yb:ybOrderPay:edit')")
    @Log(title = "支付记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbOrderPayBo bo) {
        return toAjax(iYbOrderPayService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除支付记录
     */
    @ApiOperation("删除支付记录")
    @PreAuthorize("@ss.hasPermi('yb:ybOrderPay:remove')")
    @Log(title = "支付记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbOrderPayService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
