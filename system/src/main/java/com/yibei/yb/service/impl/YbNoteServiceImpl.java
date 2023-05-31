package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.User;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.service.IUserService;
import com.yibei.yb.domain.YbTeachingMaterial;
import com.yibei.yb.domain.YbTeachingMaterialEntry;
import com.yibei.yb.domain.vo.YbTeachingMaterialEntryVo;
import com.yibei.yb.domain.vo.YbTeachingMaterialVo;
import com.yibei.yb.service.IYbTeachingMaterialEntryService;
import com.yibei.yb.service.IYbTeachingMaterialService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbNoteBo;
import com.yibei.yb.domain.vo.YbNoteVo;
import com.yibei.yb.domain.YbNote;
import com.yibei.yb.mapper.YbNoteMapper;
import com.yibei.yb.service.IYbNoteService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 笔记Service业务层处理
 *
 * @author yibei
 * @date 2022-05-11
 */
@Service
public class YbNoteServiceImpl extends ServicePlusImpl<YbNoteMapper, YbNote, YbNoteVo> implements IYbNoteService {

    @Resource
    private IUserService iUserService;
    @Resource
    private IYbTeachingMaterialEntryService iYbTeachingMaterialEntryService;
    @Resource
    private IYbTeachingMaterialService iYbTeachingMaterialService;

    @Override
    public YbNoteVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbNoteVo> queryPageList(YbNoteBo bo) {
        PagePlus<YbNote, YbNoteVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbNoteVo> queryList(YbNoteBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbNote> buildQueryWrapper(YbNoteBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbNote> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbNote::getId);
        lqw.eq(bo.getTeachingMaterialId() != null, YbNote::getTeachingMaterialId, bo.getTeachingMaterialId());
        lqw.eq(bo.getEntryId() != null, YbNote::getEntryId, bo.getEntryId());
        lqw.eq(bo.getUserId() != null, YbNote::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), YbNote::getContent, bo.getContent());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbNoteBo bo) {
        YbNote add = BeanUtil.toBean(bo, YbNote.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbNoteBo bo) {
        YbNote update = BeanUtil.toBean(bo, YbNote.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbNote entity){
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
    public YbNoteVo queryExtData(YbNoteVo vo) {
        List<YbNoteVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbNoteVo> queryExtData(List<YbNoteVo> list){
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
        String teachingMaterialIds = list.stream().map(s -> s.getTeachingMaterialId().toString()).collect(Collectors.joining(","));
        teachingMaterialIds = StringUtils.isBlank(teachingMaterialIds)?"0":teachingMaterialIds;
        List<YbTeachingMaterialVo> ybTeachingMaterialVoList = iYbTeachingMaterialService.listVo(new LambdaQueryWrapper<YbTeachingMaterial>().inSql(YbTeachingMaterial::getId, teachingMaterialIds));
        list.forEach(item->{
        item.getExtData().put("ybTeachingMaterial", ybTeachingMaterialVoList.stream().filter(s -> s.getId().compareTo(item.getTeachingMaterialId()) == 0).findFirst().orElse(null));
        });

        return list;
    }
}
