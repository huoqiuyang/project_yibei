package com.yibei.client.controller.yb;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.BeanCopyUtils;
import com.yibei.common.utils.PageUtils;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.yb.domain.YbVipCommodity;
import com.yibei.yb.domain.clientVo.YbVipCommodityInfoVo;
import com.yibei.yb.service.IYbVipCommodityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("Client_VipCommodityController")
@Api(value = "会员中心模块",tags = "会员中心模块")
@RequestMapping("/client/vipCommodity")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class VipCommodityController extends BaseController {

    private final IYbVipCommodityService iYbVipCommodityService;

    @ApiOperation("会员列表")
    @PostMapping("/list")
    public TableDataInfo<YbVipCommodityInfoVo> list(@RequestBody PageBo bo) {
        LambdaQueryWrapper<YbVipCommodity> lqw = new LambdaQueryWrapper<>();
        lqw.eq(YbVipCommodity::getStatus,1);

        lqw.orderByAsc(YbVipCommodity::getSort);
        lqw.orderByDesc(YbVipCommodity::getCreateTime);

        Page<YbVipCommodity> page = iYbVipCommodityService.page(new Page<>(bo.getPageNum(), bo.getPageSize()), lqw);
        List<YbVipCommodityInfoVo> listVo = BeanCopyUtils.listCopy(page.getRecords(),new CopyOptions(), YbVipCommodityInfoVo.class);
        return PageUtils.buildDataInfo(listVo,page.getTotal());
    }

}
