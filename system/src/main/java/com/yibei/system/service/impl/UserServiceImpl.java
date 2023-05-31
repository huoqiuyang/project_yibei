package com.yibei.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.TimeUtils;
import com.yibei.yb.domain.YbUserVip;
import com.yibei.yb.service.IYbUserVipService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.system.domain.bo.UserBo;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.domain.User;
import com.yibei.system.mapper.UserMapper;
import com.yibei.system.service.IUserService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 用户Service业务层处理
 *
 * @author yibei
 * @date 2022-04-26
 */
@Service
public class UserServiceImpl extends ServicePlusImpl<UserMapper, User, UserVo> implements IUserService {

    @Resource
    private IYbUserVipService iYbUserVipService;

    @Override
    public UserVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<UserVo> queryPageList(UserBo bo) {
        PagePlus<User, UserVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<UserVo> queryList(UserBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<User> buildQueryWrapper(UserBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<User> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(User::getCreateTime);
//        lqw.orderByDesc(User::getId);
        lqw.eq(StringUtils.isNotBlank(bo.getEmail()), User::getEmail, bo.getEmail());
        lqw.eq(StringUtils.isNotBlank(bo.getPassword()), User::getPassword, bo.getPassword());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), User::getUserName, bo.getUserName());
        lqw.like(StringUtils.isNotBlank(bo.getNickName()), User::getNickName, bo.getNickName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), User::getPhone, bo.getPhone());
        lqw.like(StringUtils.isNotBlank(bo.getRealName()), User::getRealName, bo.getRealName());
        lqw.eq(StringUtils.isNotBlank(bo.getAvatar()), User::getAvatar, bo.getAvatar());
        lqw.eq(bo.getStatus() != null, User::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getLoginIp()), User::getLoginIp, bo.getLoginIp());
        lqw.eq(bo.getLoginTime() != null, User::getLoginTime, bo.getLoginTime());
        lqw.eq(bo.getLoginCount() != null, User::getLoginCount, bo.getLoginCount());
        lqw.eq(StringUtils.isNotBlank(bo.getWeChat()), User::getWeChat, bo.getWeChat());

        if(StringUtils.isNotBlank(bo.getKeyword())){
            lqw.and(s-> s.like(User::getPhone,bo.getKeyword())
                    .or().like(User::getUserName,bo.getKeyword())
                    .or().like(User::getRealName,bo.getKeyword())
                    .or().like(User::getNickName,bo.getKeyword())
                    .or().like(User::getEmail,bo.getKeyword()));
        }

        return lqw;
    }

    @Override
    public Boolean insertByBo(UserBo bo) {
        User add = BeanUtil.toBean(bo, User.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(UserBo bo) {
        User update = BeanUtil.toBean(bo, User.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(User entity){
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
    public UserVo queryExtData(UserVo vo) {
        List<UserVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<UserVo> queryExtData(List<UserVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }

    @Override
    public Page<UserVo> getList(UserBo bo) {
        return baseMapper.getList(new Page(bo.getPageNum(),bo.getPageSize()),bo.getKeyword(),bo.getVipStatus(), TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public YbUserVip getUserVipInfo(Long userId) {

        YbUserVip userVip = null;
        if(userId!=null && !userId.equals(0l)){
            userVip = iYbUserVipService.getOne(new LambdaQueryWrapper<YbUserVip>()
                    .eq(YbUserVip::getUserId,userId)
                    .gt(YbUserVip::getEndTime, TimeUtils.getCurrentDate("yyyy-MM-dd HH:mm:ss"))
                    .last("LIMIT 1"));
        }
        return userVip;
    }
}
