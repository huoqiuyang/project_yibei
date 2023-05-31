package com.yibei.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.system.domain.SysLogininfor;
import com.yibei.system.mapper.SysLogininforMapper;
import com.yibei.system.service.ISysLogininforService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author
 */
@Service
public class SysLogininforServiceImpl extends ServicePlusImpl<SysLogininforMapper, SysLogininfor, SysLogininfor> implements ISysLogininforService {

    @Override
    public TableDataInfo<SysLogininfor> selectPageLogininforList(SysLogininfor logininfor) {
        Map<String, Object> params = logininfor.getParams();
        LambdaQueryWrapper<SysLogininfor> lqw = new LambdaQueryWrapper<SysLogininfor>()
                .like(StringUtils.isNotBlank(logininfor.getIpaddr()), SysLogininfor::getIpaddr, logininfor.getIpaddr())
                .eq(StringUtils.isNotBlank(logininfor.getStatus()), SysLogininfor::getStatus, logininfor.getStatus())
                .like(StringUtils.isNotBlank(logininfor.getUserName()), SysLogininfor::getUserName, logininfor.getUserName())
                .apply(StringUtils.isNotEmpty(params.get("beginTime")),
                        "date_format(login_time,'%y%m%d') >= date_format({0},'%y%m%d')",
                        params.get("beginTime"))
                .apply(StringUtils.isNotEmpty(params.get("endTime")),
                        "date_format(login_time,'%y%m%d') <= date_format({0},'%y%m%d')",
                        params.get("endTime"));
        return PageUtils.buildDataInfo(page(PageUtils.buildPage("info_id","desc"), lqw));
    }

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        logininfor.setLoginTime(new Date());
        save(logininfor);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        Map<String, Object> params = logininfor.getParams();
        return list(new LambdaQueryWrapper<SysLogininfor>()
                .like(StringUtils.isNotBlank(logininfor.getIpaddr()),SysLogininfor::getIpaddr,logininfor.getIpaddr())
                .eq(StringUtils.isNotBlank(logininfor.getStatus()),SysLogininfor::getStatus,logininfor.getStatus())
                .like(StringUtils.isNotBlank(logininfor.getUserName()),SysLogininfor::getUserName,logininfor.getUserName())
                .apply(StringUtils.isNotEmpty(params.get("beginTime")),
                        "date_format(login_time,'%y%m%d') >= date_format({0},'%y%m%d')",
                        params.get("beginTime"))
                .apply(StringUtils.isNotEmpty(params.get("endTime")),
                        "date_format(login_time,'%y%m%d') <= date_format({0},'%y%m%d')",
                        params.get("endTime"))
                .orderByDesc(SysLogininfor::getInfoId));
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return
     */
    @Override
    public int deleteLogininforByIds(Long[] infoIds) {
        return baseMapper.deleteBatchIds(Arrays.asList(infoIds));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        remove(new LambdaQueryWrapper<>());
    }
}
