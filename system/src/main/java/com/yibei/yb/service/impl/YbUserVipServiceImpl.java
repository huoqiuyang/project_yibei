package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.User;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.service.IUserService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbUserVipBo;
import com.yibei.yb.domain.vo.YbUserVipVo;
import com.yibei.yb.domain.YbUserVip;
import com.yibei.yb.mapper.YbUserVipMapper;
import com.yibei.yb.service.IYbUserVipService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 用户会员Service业务层处理
 *
 * @author yibei
 * @date 2022-04-27
 */
@Service
public class YbUserVipServiceImpl extends ServicePlusImpl<YbUserVipMapper, YbUserVip, YbUserVipVo> implements IYbUserVipService {

    @Resource
    private IUserService iUserService;

    @Override
    public YbUserVipVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbUserVipVo> queryPageList(YbUserVipBo bo) {
        PagePlus<YbUserVip, YbUserVipVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbUserVipVo> queryList(YbUserVipBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbUserVip> buildQueryWrapper(YbUserVipBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbUserVip> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbUserVip::getId);
        lqw.eq(bo.getUserId() != null, YbUserVip::getUserId, bo.getUserId());
        lqw.eq(bo.getStartTime() != null, YbUserVip::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, YbUserVip::getEndTime, bo.getEndTime());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbUserVipBo bo) {
        YbUserVip add = BeanUtil.toBean(bo, YbUserVip.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbUserVipBo bo) {
        YbUserVip update = BeanUtil.toBean(bo, YbUserVip.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbUserVip entity){
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
    public YbUserVipVo queryExtData(YbUserVipVo vo) {
        List<YbUserVipVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbUserVipVo> queryExtData(List<YbUserVipVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String userIds = list.stream().map(s -> s.getUserId().toString()).collect(Collectors.joining(","));
        userIds = StringUtils.isBlank(userIds)?"0":userIds;
        List<UserVo> userVoList = iUserService.listVo(new LambdaQueryWrapper<User>().inSql(User::getId, userIds));
        list.forEach(item->{
        item.getExtData().put("user", userVoList.stream().filter(s -> s.getId().compareTo(item.getUserId()) == 0).findFirst().orElse(null));
        });

        return list;
    }
}
