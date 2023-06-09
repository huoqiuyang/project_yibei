package com.yibei.admin.controller.system;

import com.yibei.common.core.validate.QueryGroup;
import com.yibei.common.enums.BusinessType;
import com.yibei.system.domain.bo.SysOssConfigBo;
import com.yibei.system.domain.vo.SysOssConfigVo;
import com.yibei.system.service.ISysOssConfigService;
import com.yibei.common.annotation.Log;
import com.yibei.common.annotation.RepeatSubmit;
import com.yibei.common.core.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.core.validate.AddGroup;
import com.yibei.common.core.validate.EditGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * 云存储配置Controller
 *
 * @author Lion Li
 * @author 孤舟烟雨
 * @date 2021-08-13
 */
@Validated
@Api(value = "云存储配置控制器", tags = {"云存储配置管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/system/oss/config")
public class SysOssConfigController extends BaseController {

	private final ISysOssConfigService iSysOssConfigService;

	/**
	 * 查询云存储配置列表
	 */
	@ApiOperation("查询云存储配置列表")
//	@PreAuthorize("@ss.hasPermi('system:oss:list')")
	@GetMapping("/list")
	public TableDataInfo<SysOssConfigVo> list(@Validated(QueryGroup.class) SysOssConfigBo bo) {
		return iSysOssConfigService.queryPageList(bo);
	}

	/**
	 * 获取云存储配置详细信息
	 */
	@ApiOperation("获取云存储配置详细信息")
//	@PreAuthorize("@ss.hasPermi('system:oss:query')")
	@GetMapping("/{ossConfigId}")
	public AjaxResult<SysOssConfigVo> getInfo(@NotNull(message = "主键不能为空")
											  @PathVariable("ossConfigId") Integer ossConfigId) {
		return AjaxResult.success(iSysOssConfigService.queryById(ossConfigId));
	}

	/**
	 * 新增云存储配置
	 */
	@ApiOperation("新增云存储配置")
//	@PreAuthorize("@ss.hasPermi('system:oss:add')")
	@Log(title = "云存储配置", businessType = BusinessType.INSERT)
	@RepeatSubmit()
	@PostMapping()
	public AjaxResult<Void> add(@Validated(AddGroup.class) @RequestBody SysOssConfigBo bo) {
		return toAjax(iSysOssConfigService.insertByBo(bo) ? 1 : 0);
	}

	/**
	 * 修改云存储配置
	 */
	@ApiOperation("修改云存储配置")
//	@PreAuthorize("@ss.hasPermi('system:oss:edit')")
	@Log(title = "云存储配置", businessType = BusinessType.UPDATE)
	@RepeatSubmit()
	@PutMapping()
	public AjaxResult<Void> edit(@Validated(EditGroup.class) @RequestBody SysOssConfigBo bo) {
		return toAjax(iSysOssConfigService.updateByBo(bo) ? 1 : 0);
	}

	/**
	 * 删除云存储配置
	 */
	@ApiOperation("删除云存储配置")
//	@PreAuthorize("@ss.hasPermi('system:oss:remove')")
	@Log(title = "云存储配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ossConfigIds}")
	public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
								   @PathVariable Long[] ossConfigIds) {
		return toAjax(iSysOssConfigService.deleteWithValidByIds(Arrays.asList(ossConfigIds), true) ? 1 : 0);
	}

	/**
	 * 状态修改
	 */
//	@PreAuthorize("@ss.hasPermi('system:oss:edit')")
	@Log(title = "云存储状态修改", businessType = BusinessType.UPDATE)
	@PutMapping("/changeStatus")
	public AjaxResult changeStatus(@RequestBody SysOssConfigBo bo) {
		return toAjax(iSysOssConfigService.updateOssConfigStatus(bo));
	}
}
