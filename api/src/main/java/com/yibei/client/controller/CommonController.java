package com.yibei.client.controller;

import cn.hutool.core.util.ObjectUtil;
import com.yibei.common.annotation.RepeatSubmit;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.exception.ServiceException;
import com.yibei.system.domain.SysOss;
import com.yibei.system.service.ISysDictTypeService;
import com.yibei.system.service.ISysOssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController("Client_CommonController")
@Api(value = "通用接口模块",tags = "通用接口模块")
@RequestMapping("/client/common")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommonController extends BaseController {

    private final ISysOssService iSysOssService;
    private final ISysDictTypeService iSysDictTypeService;

    @ApiOperation("上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", dataType = "java.io.File", required = true)
    })
    @RepeatSubmit
    @PostMapping("/upload")
    public AjaxResult<Map<String, String>> upload(@RequestPart("file") MultipartFile file,String cate) {
        if (ObjectUtil.isNull(file)) {
            throw new ServiceException("上传文件不能为空");
        }
        SysOss oss = iSysOssService.upload(file,cate,getUserId());
        Map<String, String> map = new HashMap<>(2);
        map.put("url", oss.getUrl());
        map.put("fileName", oss.getFileName());
        return AjaxResult.success(map);
    }
}
