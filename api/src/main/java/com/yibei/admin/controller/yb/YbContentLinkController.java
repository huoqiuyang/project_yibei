package com.yibei.admin.controller.yb;

import java.util.List;
import java.util.Arrays;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.common.exception.ServiceException;
import com.yibei.yb.domain.YbContentLink;
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
import com.yibei.yb.domain.vo.YbContentLinkVo;
import com.yibei.yb.domain.bo.YbContentLinkBo;
import com.yibei.yb.service.IYbContentLinkService;
import com.yibei.common.core.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 相关链接Controller
 *
 * @author yibei
 * @date 2022-05-06
 */
@Validated
@Api(value = "相关链接控制器", tags = {"相关链接管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/yb/ybContentLink")
public class YbContentLinkController extends BaseController {

    private final IYbContentLinkService iYbContentLinkService;

    /**
     * 查询相关链接列表
     */
    @ApiOperation("查询相关链接列表")
    @PreAuthorize("@ss.hasPermi('yb:ybContentLink:list')")
    @GetMapping("/list")
    public TableDataInfo<YbContentLinkVo> list(@Validated(QueryGroup.class) YbContentLinkBo bo) {
        TableDataInfo<YbContentLinkVo> tableDataInfo = iYbContentLinkService.queryPageList(bo);
        //数据联查操作
        iYbContentLinkService.queryExtData(tableDataInfo.getRows());
        return tableDataInfo;
    }

    /**
     * 导出相关链接列表
     */
    @ApiOperation("导出相关链接列表")
    @PreAuthorize("@ss.hasPermi('yb:ybContentLink:export')")
    @Log(title = "相关链接", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(@Validated YbContentLinkBo bo, HttpServletResponse response) {
        List<YbContentLinkVo> list = iYbContentLinkService.queryList(bo);
        ExcelUtil.exportExcel(list, "相关链接", YbContentLinkVo.class, response);
    }

    /**
     * 获取相关链接详细信息
     */
    @ApiOperation("获取相关链接详细信息")
    @PreAuthorize("@ss.hasPermi('yb:ybContentLink:query')")
    @GetMapping("/{id}")
    public AjaxResult<YbContentLinkVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable("id") Long id) {
        YbContentLinkVo vo = iYbContentLinkService.queryById(id);
        //vo = iYbContentLinkService.queryExtData(vo);
        return AjaxResult.success(vo);
    }

    /**
     * 新增相关链接
     */
    @ApiOperation("新增相关链接")
    @PreAuthorize("@ss.hasPermi('yb:ybContentLink:add')")
    @Log(title = "相关链接", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody YbContentLinkBo bo) {

        //避免重复添加相关链接
        YbContentLink contentLink = iYbContentLinkService.getOne(new LambdaQueryWrapper<YbContentLink>()
                .eq(YbContentLink::getSourceType,bo.getSourceType())
                .eq(YbContentLink::getEntryId,bo.getEntryId())
                .eq(YbContentLink::getLinkSourceType,bo.getLinkSourceType())
                .eq(YbContentLink::getLinkEntryId,bo.getLinkEntryId())
                .last("LIMIT 1"));
        if(contentLink!=null){
            throw new ServiceException("已有此相关链接");
        }

        return toAjax(iYbContentLinkService.insertByBo(bo) ? 1 : 0);
    }

    /**
     * 修改相关链接
     */
    @ApiOperation("修改相关链接")
    @PreAuthorize("@ss.hasPermi('yb:ybContentLink:edit')")
    @Log(title = "相关链接", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody YbContentLinkBo bo) {
        return toAjax(iYbContentLinkService.updateByBo(bo) ? 1 : 0);
    }

    /**
     * 删除相关链接
     */
    @ApiOperation("删除相关链接")
    @PreAuthorize("@ss.hasPermi('yb:ybContentLink:remove')")
    @Log(title = "相关链接" , businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
                                       @PathVariable Long[] ids) {
        return toAjax(iYbContentLinkService.deleteWithValidByIds(Arrays.asList(ids), true) ? 1 : 0);
    }
}
