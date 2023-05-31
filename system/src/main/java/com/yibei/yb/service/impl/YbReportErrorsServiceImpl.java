package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.User;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.service.IUserService;
import com.yibei.yb.domain.YbTeachingMaterialEntry;
import com.yibei.yb.domain.vo.YbTeachingMaterialEntryVo;
import com.yibei.yb.service.IYbTeachingMaterialEntryService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbReportErrorsBo;
import com.yibei.yb.domain.vo.YbReportErrorsVo;
import com.yibei.yb.domain.YbReportErrors;
import com.yibei.yb.mapper.YbReportErrorsMapper;
import com.yibei.yb.service.IYbReportErrorsService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 报错Service业务层处理
 *
 * @author yibei
 * @date 2022-05-18
 */
@Service
public class YbReportErrorsServiceImpl extends ServicePlusImpl<YbReportErrorsMapper, YbReportErrors, YbReportErrorsVo> implements IYbReportErrorsService {

    @Resource
    private IUserService iUserService;
    @Resource
    private IYbTeachingMaterialEntryService iYbTeachingMaterialEntryService;

    @Override
    public YbReportErrorsVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbReportErrorsVo> queryPageList(YbReportErrorsBo bo) {
        PagePlus<YbReportErrors, YbReportErrorsVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbReportErrorsVo> queryList(YbReportErrorsBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbReportErrors> buildQueryWrapper(YbReportErrorsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbReportErrors> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbReportErrors::getCreateTime);
//        lqw.orderByDesc(YbReportErrors::getId);
        lqw.eq(bo.getUserId() != null, YbReportErrors::getUserId, bo.getUserId());
        lqw.eq(bo.getEntryId() != null, YbReportErrors::getEntryId, bo.getEntryId());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), YbReportErrors::getContent, bo.getContent());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrl()), YbReportErrors::getImgUrl, bo.getImgUrl());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbReportErrorsBo bo) {
        YbReportErrors add = BeanUtil.toBean(bo, YbReportErrors.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbReportErrorsBo bo) {
        YbReportErrors update = BeanUtil.toBean(bo, YbReportErrors.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbReportErrors entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public YbReportErrorsVo queryExtData(YbReportErrorsVo vo) {
        List<YbReportErrorsVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbReportErrorsVo> queryExtData(List<YbReportErrorsVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String userIds = list.stream().map(s -> s.getUserId().toString()).collect(Collectors.joining(","));
        userIds = StringUtils.isBlank(userIds)?"0":userIds;
        List<UserVo> userVoList = iUserService.listVo(new LambdaQueryWrapper<User>().inSql(User::getId, userIds));
        list.forEach(item->{
            item.getExtData().put("user", userVoList.stream().filter(s -> s.getId().compareTo(item.getUserId()) == 0).findFirst().orElse(null));
        });
        String entryIds = list.stream().map(s -> s.getEntryId().toString()).collect(Collectors.joining(","));
        entryIds = StringUtils.isBlank(entryIds)?"0":entryIds;
        List<YbTeachingMaterialEntryVo> ybTeachingMaterialEntryVoList = iYbTeachingMaterialEntryService.listVo(new LambdaQueryWrapper<YbTeachingMaterialEntry>().inSql(YbTeachingMaterialEntry::getId, entryIds));
        list.forEach(item->{
            item.getExtData().put("ybTeachingMaterialEntry", ybTeachingMaterialEntryVoList.stream().filter(s -> s.getId().compareTo(item.getEntryId()) == 0).findFirst().orElse(null));
        });

        return list;
    }
}