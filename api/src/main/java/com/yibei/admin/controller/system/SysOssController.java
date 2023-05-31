package com.yibei.admin.controller.system;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.common.core.validate.QueryGroup;
import com.yibei.common.enums.BusinessType;
import com.yibei.common.utils.JsonUtils;
import com.yibei.common.utils.file.FileUtils;
import com.yibei.system.domain.SysConfig;
import com.yibei.system.domain.SysOss;
import com.yibei.system.domain.bo.SysOssBo;
import com.yibei.system.domain.vo.SysOssVo;
import com.yibei.system.service.ISysConfigService;
import com.yibei.system.service.ISysOssService;
import com.yibei.common.annotation.Log;
import com.yibei.common.annotation.RepeatSubmit;
import com.yibei.common.core.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.exception.ServiceException;
import com.yibei.oss.constant.CloudConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传 控制层
 *
 * @author Lion Li
 */
@Validated
@Api(value = "OSS云存储控制器", tags = {"OSS云存储管理"})
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping("/admin/system/oss")
public class SysOssController extends BaseController {

	private final ISysOssService iSysOssService;
	private final ISysConfigService iSysConfigService;

	/**
	 * 查询OSS云存储列表
	 */
	@ApiOperation("查询OSS云存储列表")
//	@PreAuthorize("@ss.hasPermi('system:oss:list')")
	@GetMapping("/list")
	public TableDataInfo<SysOssVo> list(@Validated(QueryGroup.class) SysOssBo bo) {
		return iSysOssService.queryPageList(bo);
	}

	/**
	 * 上传OSS云存储
	 */
	@ApiOperation("上传OSS云存储")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "file", value = "文件", dataType = "java.io.File", required = true),
	})
//	@PreAuthorize("@ss.hasPermi('system:oss:upload')")
	@Log(title = "OSS云存储", businessType = BusinessType.INSERT)
	@RepeatSubmit
	@PostMapping("/upload")
	public AjaxResult<Map<String, String>> upload(@RequestPart("file") MultipartFile file,String cate) {
		if (ObjectUtil.isNull(file)) {
			throw new ServiceException("上传文件不能为空");
		}
		SysOss oss = iSysOssService.upload(file,cate,0L);
		Map<String, String> map = new HashMap<>(2);
		map.put("url", oss.getUrl());
		map.put("fileName", oss.getFileName());
		return AjaxResult.success(map);
	}

	@ApiOperation("下载OSS云存储")
//	@PreAuthorize("@ss.hasPermi('system:oss:download')")
	@GetMapping("/download/{ossId}")
	public void download(@PathVariable Long ossId, HttpServletResponse response) throws IOException {
		SysOss sysOss = iSysOssService.getById(ossId);
		if (ObjectUtil.isNull(sysOss)) {
			throw new ServiceException("文件数据不存在!");
		}
		response.reset();
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
		FileUtils.setAttachmentResponseHeader(response, URLEncoder.encode(sysOss.getOriginalName(), StandardCharsets.UTF_8.toString()));
		response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE + "; charset=UTF-8");
		long data = HttpUtil.download(sysOss.getUrl(), response.getOutputStream(), false);
		response.setContentLength(Convert.toInt(data));
	}

	/**
	 * 删除OSS云存储
	 */
	@ApiOperation("删除OSS云存储")
//	@PreAuthorize("@ss.hasPermi('system:oss:remove')")
	@Log(title = "OSS云存储" , businessType = BusinessType.DELETE)
	@DeleteMapping("/{ossIds}")
	public AjaxResult<Void> remove(@NotEmpty(message = "主键不能为空")
								   @PathVariable Long[] ossIds) {
		return toAjax(iSysOssService.deleteWithValidByIds(Arrays.asList(ossIds), true) ? 1 : 0);
	}

	/**
	 * 变更图片列表预览状态
	 */
	@ApiOperation("变更图片列表预览状态")
//	@PreAuthorize("@ss.hasPermi('system:oss:edit')")
	@Log(title = "OSS云存储" , businessType = BusinessType.UPDATE)
	@PutMapping("/changePreviewListResource")
	public AjaxResult<Void> changePreviewListResource(@RequestBody String body) {
		Map<String, Boolean> map = JsonUtils.parseMap(body);
		SysConfig config = iSysConfigService.getOne(new LambdaQueryWrapper<SysConfig>()
			.eq(SysConfig::getConfigKey, CloudConstant.PEREVIEW_LIST_RESOURCE_KEY));
		config.setConfigValue(map.get("previewListResource").toString());
		return toAjax(iSysConfigService.updateConfig(config));
	}

}
