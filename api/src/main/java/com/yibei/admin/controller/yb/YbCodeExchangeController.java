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
import com.yibei.yb.domain.vo.YbCodeExchangeVo;
import com.yibei.yb.domain.bo.YbCodeExchangeBo;
import com.yibei.yb.service.IYbCodeExchangeService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 兑换记录Controller
 *
 * @author yibei
 * @date 2022-05-26
 */
@Validated
@Api(value = "兑换记录控制器", tags = {"兑换记录管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybCodeExchange")
public class YbCodeExchangeController extends BaseController {

    private final IYbCodeExchangeService iYbCodeExchangeService;

    /**
     * 查询兑换记录列表
     */
    @ApiOperation("查询兑换记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybCodeExchange:list')")
    @GetMapping("/list")
    public TableDataInfo<YbCodeExchangeVo> list(@Validated(QueryGroup.class) YbCodeExchangeBo bo) {
        TableDataInfo<YbCodeExchangeVo> tableDataInfo = iYbCodeExchangeService.queryPageList(bo);
        //数据联查操作
        iYbCodeExchangeService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出兑换记录列表
     */
    @ApiOperation("导出兑换记录列表")
    @PreAuthorize("@ss.hasPermi('yb:ybCodeExchange:export')")
    @Log(title = "兑换记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbCodeExchangeBo bo, HttpServletResponse response) {
        List<YbCodeExchangeVo> list = iYbCodeExchangeService.queryList(bo);
        ExcelUtil.exportExcel(list, "兑换记录", YbCodeExchangeVo.class, response);
    }

    /**
     * 获取兑换记录详细信息
     */
    @ApiOperation("获取兑换记录详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybCodeExchange:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbCodeExchangeVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbCodeExchangeVo vo = iYbCodeExchangeService.queryById(id);
        //vo = iYbCodeExchangeService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增兑换记录
     */
    @ApiOperation("新增兑换记录")
    @PreAuthorize("@ss.hasPermi('yb:ybCodeExchange:add')")
    @Log(title = "兑换记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbCodeExchangeBo bo) {
        return toAjax(iYbCodeExchangeService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改兑换记录
     */
    @ApiOperation("修改兑换记录")
    @PreAuthorize("@ss.hasPermi('yb:ybCodeExchange:edit')")
    @Log(title = "兑换记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbCodeExchangeBo bo) {
        return toAjax(iYbCodeExchangeService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除兑换记录
     */
    @ApiOperation("删除兑换记录")
    @PreAuthorize("@ss.hasPermi('yb:ybCodeExchange:remove')")
    @Log(title = "兑换记录" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbCodeExchangeService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
