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
import com.yibei.yb.domain.vo.YbQuestionBankVo;
import com.yibei.yb.domain.bo.YbQuestionBankBo;
import com.yibei.yb.service.IYbQuestionBankService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 题库Controller
 *
 * @author yibei
 * @date 2022-05-06
 */
@Validated
@Api(value = "题库控制器", tags = {"题库管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybQuestionBank")
public class YbQuestionBankController extends BaseController {

    private final IYbQuestionBankService iYbQuestionBankService;

    /**
     * 查询题库列表
     */
    @ApiOperation("查询题库列表")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBank:list')")
    @GetMapping("/list")
    public TableDataInfo<YbQuestionBankVo> list(@Validated(QueryGroup.class) YbQuestionBankBo bo) {
        TableDataInfo<YbQuestionBankVo> tableDataInfo = iYbQuestionBankService.queryPageList(bo);
        //数据联查操作
        iYbQuestionBankService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出题库列表
     */
    @ApiOperation("导出题库列表")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBank:export')")
    @Log(title = "题库", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbQuestionBankBo bo, HttpServletResponse response) {
        List<YbQuestionBankVo> list = iYbQuestionBankService.queryList(bo);
        ExcelUtil.exportExcel(list, "题库", YbQuestionBankVo.class, response);
    }

    /**
     * 获取题库详细信息
     */
    @ApiOperation("获取题库详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBank:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbQuestionBankVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbQuestionBankVo vo = iYbQuestionBankService.queryById(id);
        //vo = iYbQuestionBankService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增题库
     */
    @ApiOperation("新增题库")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBank:add')")
    @Log(title = "题库", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbQuestionBankBo bo) {
        return toAjax(iYbQuestionBankService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改题库
     */
    @ApiOperation("修改题库")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBank:edit')")
    @Log(title = "题库", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbQuestionBankBo bo) {
        return toAjax(iYbQuestionBankService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除题库
     */
    @ApiOperation("删除题库")
    @PreAuthorize("@ss.hasPermi('yb:ybQuestionBank:remove')")
    @Log(title = "题库" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbQuestionBankService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
