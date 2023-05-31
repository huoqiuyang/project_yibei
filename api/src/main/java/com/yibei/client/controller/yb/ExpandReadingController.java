package com.yibei.client.controller.yb;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.BeanCopyUtils;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.utils.TimeUtils;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.system.domain.clientBo.IdBo;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.yb.domain.YbExpandReading;
import com.yibei.yb.domain.YbReadingLog;
import com.yibei.yb.domain.YbUserVip;
import com.yibei.yb.domain.clientBo.YbExpandReadingUpdateStatusBo;
import com.yibei.yb.domain.clientVo.YbExpandReadingInfoVo;
import com.yibei.yb.service.IYbExpandReadingService;
import com.yibei.yb.service.IYbReadingLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("Client_ExpandReadingController")
@Api(value = "拓展阅读模块",tags = "拓展阅读模块")
@RequestMapping("/client/expandReading")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ExpandReadingController extends BaseController {

    private final IYbExpandReadingService iYbExpandReadingService;

    private final IYbReadingLogService iYbReadingLogService;

    @ApiOperation("拓展阅读列表")
    @PostMapping("/list")
    public TableDataInfo<YbExpandReadingInfoVo> list(@RequestBody PageBo bo) {
        LambdaQueryWrapper<YbExpandReading> lqw = new LambdaQueryWrapper<>();
        lqw.eq(YbExpandReading::getStatus,1);

        lqw.orderByAsc(YbExpandReading::getSort);
        lqw.orderByDesc(YbExpandReading::getCreateTime);

        Page<YbExpandReading> page = iYbExpandReadingService.page(new Page<>(bo.getPageNum(), bo.getPageSize()), lqw);
        List<YbExpandReadingInfoVo> listVo = BeanCopyUtils.listCopy(page.getRecords(),new CopyOptions(),YbExpandReadingInfoVo.class);

        Long userId = getUserId();
        if(userId!=null && !userId.equals(0l)){
            //用户阅读状态
            listVo.forEach(item->{
                YbReadingLog readingLog = getReadingLog(userId,item.getId());
                if(readingLog!=null){
                    item.setReadStatus(readingLog.getStatus());
                }
            });
        }

        return PageUtils.buildDataInfo(listVo,page.getTotal());
    }

    @ApiOperation("拓展阅读详情")
    @PostMapping("/detail")
    @LoginChecked
    public AjaxResult<YbExpandReadingInfoVo> detail(@RequestBody IdBo bo){

        //验证会员，非会员用户不能进入详情页面
        YbUserVip ybUserVip = iUserService.getUserVipInfo(getUserId());
        if(ybUserVip==null){
            return AjaxResult.error("还不是会员，暂时无法查看",null);
        }

        YbExpandReading expandReading = iYbExpandReadingService.getById(bo.getId());
        if(expandReading==null){
            return AjaxResult.success("",null);
        }

        YbExpandReadingInfoVo vo = BeanCopyUtils.oneCopy(expandReading, new CopyOptions(), YbExpandReadingInfoVo.class);
        //用户阅读状态
        YbReadingLog readingLog = getReadingLog(getUserId(),bo.getId());
        if(readingLog!=null){
            vo.setReadStatus(readingLog.getStatus());
        }

        return AjaxResult.success(vo);
    }

    /**
     * 获取用户阅读记录
     * */
    private YbReadingLog getReadingLog(Long userId,Long id){
        return iYbReadingLogService.getOne(new LambdaQueryWrapper<YbReadingLog>().eq(YbReadingLog::getUserId,userId).eq(YbReadingLog::getExpandReadId,id).last("LIMIT 1"));
    }

    @ApiOperation("用户拓展阅读状态修改")
    @PostMapping("/updateReadStatus")
    @LoginChecked
    public AjaxResult modifyReadStatus(@RequestBody YbExpandReadingUpdateStatusBo bo){

        YbExpandReading expandReading = iYbExpandReadingService.getById(bo.getId());
        if(expandReading==null){
            return AjaxResult.error("文章不存在");
        }

        YbReadingLog ybReadingLog = getReadingLog(getUserId(),bo.getId());
        if(ybReadingLog!=null){
            //当前文章有阅读记录，修改阅读状态（上一次的阅读状态，当上一次为[已学完(3)]时，不再去修改状态）
            if(!ybReadingLog.getStatus().equals(3l)){
                ybReadingLog.setStatus(Long.valueOf(bo.getReadStatus()));
                ybReadingLog.setUpdateTime(TimeUtils.getCurrentDate());
                if(!iYbReadingLogService.updateById(ybReadingLog)){
                    return AjaxResult.error("阅读状态修改失败");
                }
            }else{
                return AjaxResult.success("当前文章已学完");
            }
        }else{
            //当前文章没有阅读记录，增加阅读记录
            YbReadingLog readingLog = new YbReadingLog();
            readingLog.setExpandReadId(bo.getId());
            readingLog.setUserId(getUserId());
            readingLog.setStatus(Long.valueOf(bo.getReadStatus()));
            readingLog.setUpdateTime(TimeUtils.getCurrentDate());
            if(!iYbReadingLogService.save(readingLog)){
                return AjaxResult.error("阅读状态修改失败");
            }
        }

        return AjaxResult.success("阅读状态修改成功");
    }

}
