package com.yibei.client.controller.yb;

import cn.hutool.core.bean.copier.CopyOptions;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.BeanCopyUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.yb.domain.YbReportErrors;
import com.yibei.yb.domain.clientBo.YbReportErrorsInfoBo;
import com.yibei.yb.service.IYbReportErrorsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("Client_ReportErrorsController")
@Api(value = "报错模块",tags = "报错模块")
@RequestMapping("/client/reportErrors")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReportErrorsController extends BaseController {

    private final IYbReportErrorsService iYbReportErrorsService;

    @ApiOperation("提交报错")
    @PostMapping("/submitErrInfo")
    @LoginChecked
    public AjaxResult getSignStudyTimeInfo(@RequestBody YbReportErrorsInfoBo bo) {

        YbReportErrors reportErrors = BeanCopyUtils.oneCopy(bo, new CopyOptions(), YbReportErrors.class);
        reportErrors.setUserId(getUserId());

        if(!iYbReportErrorsService.save(reportErrors)){
            return AjaxResult.error("提交失败");
        }

        return AjaxResult.success("提交成功");
    }


}
