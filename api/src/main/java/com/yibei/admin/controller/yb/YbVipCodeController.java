package com.yibei.admin.controller.yb;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.common.utils.RedeemCodeUtils;
import com.yibei.yb.domain.YbVipCode;
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
import com.yibei.yb.domain.vo.YbVipCodeVo;
import com.yibei.yb.domain.bo.YbVipCodeBo;
import com.yibei.yb.service.IYbVipCodeService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 会员兑换码Controller
 *
 * @author yibei
 * @date 2022-04-27
 */
@Validated
@Api(value = "会员兑换码控制器", tags = {"会员兑换码管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybVipCode")
public class YbVipCodeController extends BaseController {

    private final IYbVipCodeService iYbVipCodeService;

    /**
     * 查询会员兑换码列表
     */
    @ApiOperation("查询会员兑换码列表")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCode:list')")
    @GetMapping("/list")
    public TableDataInfo<YbVipCodeVo> list(@Validated(QueryGroup.class) YbVipCodeBo bo) {
        TableDataInfo<YbVipCodeVo> tableDataInfo = iYbVipCodeService.queryPageList(bo);
        //数据联查操作
        iYbVipCodeService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出会员兑换码列表
     */
    @ApiOperation("导出会员兑换码列表")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCode:export')")
    @Log(title = "会员兑换码", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbVipCodeBo bo, HttpServletResponse response) {
        List<YbVipCodeVo> list = iYbVipCodeService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员兑换码", YbVipCodeVo.class, response);
    }

    /**
     * 获取会员兑换码详细信息
     */
    @ApiOperation("获取会员兑换码详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCode:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbVipCodeVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbVipCodeVo vo = iYbVipCodeService.queryById(id);
        //vo = iYbVipCodeService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增会员兑换码
     */
    @ApiOperation("新增会员兑换码")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCode:add')")
    @Log(title = "会员兑换码", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbVipCodeBo bo) {
        bo.setCode(generateCode());
        return toAjax(iYbVipCodeService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改会员兑换码
     */
    @ApiOperation("修改会员兑换码")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCode:edit')")
    @Log(title = "会员兑换码", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbVipCodeBo bo) {
        return toAjax(iYbVipCodeService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除会员兑换码
     */
    @ApiOperation("删除会员兑换码")
    @PreAuthorize("@ss.hasPermi('yb:ybVipCode:remove')")
    @Log(title = "会员兑换码" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbVipCodeService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }

    /**
     * 生成兑换码
     * */
    private String generateCode(){
        String code = RedeemCodeUtils.createBigSmallLetterStrOrNumberRadom(12);
        //避免兑换码重复
        while(iYbVipCodeService.count(new LambdaQueryWrapper<YbVipCode>().eq(YbVipCode::getCode, code))>0){
            code = RedeemCodeUtils.createBigSmallLetterStrOrNumberRadom(12);
        }
        return code;
    }

}
