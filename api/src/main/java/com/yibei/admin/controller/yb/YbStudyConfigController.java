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
import com.yibei.yb.domain.vo.YbStudyConfigVo;
import com.yibei.yb.domain.bo.YbStudyConfigBo;
import com.yibei.yb.service.IYbStudyConfigService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 教材学习配置Controller
 *
 * @author yibei
 * @date 2022-05-12
 */
@Validated
@Api(value = "教材学习配置控制器", tags = {"教材学习配置管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybStudyConfig")
public class YbStudyConfigController extends BaseController {

    private final IYbStudyConfigService iYbStudyConfigService;

    /**
     * 查询教材学习配置列表
     */
    @ApiOperation("查询教材学习配置列表")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyConfig:list')")
    @GetMapping("/list")
    public TableDataInfo<YbStudyConfigVo> list(@Validated(QueryGroup.class) YbStudyConfigBo bo) {
        TableDataInfo<YbStudyConfigVo> tableDataInfo = iYbStudyConfigService.queryPageList(bo);
        //数据联查操作
        iYbStudyConfigService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出教材学习配置列表
     */
    @ApiOperation("导出教材学习配置列表")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyConfig:export')")
    @Log(title = "教材学习配置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbStudyConfigBo bo, HttpServletResponse response) {
        List<YbStudyConfigVo> list = iYbStudyConfigService.queryList(bo);
        ExcelUtil.exportExcel(list, "教材学习配置", YbStudyConfigVo.class, response);
    }

    /**
     * 获取教材学习配置详细信息
     */
    @ApiOperation("获取教材学习配置详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyConfig:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbStudyConfigVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbStudyConfigVo vo = iYbStudyConfigService.queryById(id);
        //vo = iYbStudyConfigService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增教材学习配置
     */
    @ApiOperation("新增教材学习配置")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyConfig:add')")
    @Log(title = "教材学习配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbStudyConfigBo bo) {
        return toAjax(iYbStudyConfigService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改教材学习配置
     */
    @ApiOperation("修改教材学习配置")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyConfig:edit')")
    @Log(title = "教材学习配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbStudyConfigBo bo) {
        return toAjax(iYbStudyConfigService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除教材学习配置
     */
    @ApiOperation("删除教材学习配置")
    @PreAuthorize("@ss.hasPermi('yb:ybStudyConfig:remove')")
    @Log(title = "教材学习配置" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbStudyConfigService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
