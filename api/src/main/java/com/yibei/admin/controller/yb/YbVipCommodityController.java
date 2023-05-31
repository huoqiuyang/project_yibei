package com.yibei.admin.controller.yb;

import java.math.BigDecimal;
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
import com.yibei.yb.domain.vo.YbVipCommodityVo;
import com.yibei.yb.domain.bo.YbVipCommodityBo;
import com.yibei.yb.service.IYbVipCommodityService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员中心Controller
 *
 * @author yibei
 * @date 2022-04-27
 */
@Validated
@Api(value = "会员中心控制器", tags = {"会员中心管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybVipCommodity")
public class YbVipCommodityController extends BaseController {

    private final IYbVipCommodityService iYbVipCommodityService;

    /**
     * 查询会员中心列表
     */
    @ApiOperation("查询会员中心列表")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCommodity:list')")
    @GetMapping("/list")
    public TableDataInfo<YbVipCommodityVo> list(@Validated(QueryGroup.class) YbVipCommodityBo bo) {
        TableDataInfo<YbVipCommodityVo> tableDataInfo = iYbVipCommodityService.queryPageList(bo);
        //数据联查操作
        iYbVipCommodityService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出会员中心列表
     */
    @ApiOperation("导出会员中心列表")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCommodity:export')")
    @Log(title = "会员中心", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbVipCommodityBo bo, HttpServletResponse response) {
        List<YbVipCommodityVo> list = iYbVipCommodityService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员中心", YbVipCommodityVo.class, response);
    }

    /**
     * 获取会员中心详细信息
     */
    @ApiOperation("获取会员中心详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCommodity:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbVipCommodityVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbVipCommodityVo vo = iYbVipCommodityService.queryById(id);
        //vo = iYbVipCommodityService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增会员中心
     */
    @ApiOperation("新增会员中心")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCommodity:add')")
    @Log(title = "会员中心", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbVipCommodityBo bo) {
        bo.setPrecioMensual(precioMensual(bo.getPrice(), bo.getMonth()));
        return toAjax(iYbVipCommodityService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改会员中心
     */
    @ApiOperation("修改会员中心")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCommodity:edit')")
    @Log(title = "会员中心", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbVipCommodityBo bo) {
        bo.setPrecioMensual(precioMensual(bo.getPrice(), bo.getMonth()));
        return toAjax(iYbVipCommodityService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除会员中心
     */
    @ApiOperation("删除会员中心")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCommodity:remove')")
    @Log(title = "会员中心" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbVipCommodityService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 月单价 = 金额/月份
     * */
    private BigDecimal precioMensual(BigDecimal price,Integer month){
        BigDecimal precioMensual = new BigDecimal(0);

        try {
            BigDecimal monthNum = new BigDecimal(month);
            if(monthNum.compareTo(BigDecimal.ZERO)!=0){
                precioMensual = price.divide(monthNum,1,BigDecimal.ROUND_HALF_UP).stripTrailingZeros();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return precioMensual;
    }

}
