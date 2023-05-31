package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.system.domain.User;
import com.yibei.system.domain.vo.UserVo;
import com.yibei.system.service.IUserService;
import com.yibei.yb.domain.YbVipCode;
import com.yibei.yb.domain.vo.YbVipCodeVo;
import com.yibei.yb.service.IYbVipCodeService;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbCodeExchangeBo;
import com.yibei.yb.domain.vo.YbCodeExchangeVo;
import com.yibei.yb.domain.YbCodeExchange;
import com.yibei.yb.mapper.YbCodeExchangeMapper;
import com.yibei.yb.service.IYbCodeExchangeService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 兑换记录Service业务层处理
 *
 * @author yibei
 * @date 2022-05-26
 */
@Service
public class YbCodeExchangeServiceImpl extends ServicePlusImpl<YbCodeExchangeMapper, YbCodeExchange, YbCodeExchangeVo> implements IYbCodeExchangeService {

    @Resource
    private IUserService iUserService;
    @Resource
    private IYbVipCodeService iYbVipCodeService;

    @Override
    public YbCodeExchangeVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbCodeExchangeVo> queryPageList(YbCodeExchangeBo bo) {
        PagePlus<YbCodeExchange, YbCodeExchangeVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbCodeExchangeVo> queryList(YbCodeExchangeBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbCodeExchange> buildQueryWrapper(YbCodeExchangeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbCodeExchange> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbCodeExchange::getId);
        lqw.eq(bo.getUserId() != null, YbCodeExchange::getUserId, bo.getUserId());
        lqw.eq(bo.getCodeId() != null, YbCodeExchange::getCodeId, bo.getCodeId());
        lqw.eq(bo.getVipDay() != null, YbCodeExchange::getVipDay, bo.getVipDay());
        lqw.eq(StringUtils.isNotBlank(bo.getCode()), YbCodeExchange::getCode, bo.getCode());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbCodeExchangeBo bo) {
        YbCodeExchange add = BeanUtil.toBean(bo, YbCodeExchange.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbCodeExchangeBo bo) {
        YbCodeExchange update = BeanUtil.toBean(bo, YbCodeExchange.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbCodeExchange entity){
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
    public YbCodeExchangeVo queryExtData(YbCodeExchangeVo vo) {
        List<YbCodeExchangeVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbCodeExchangeVo> queryExtData(List<YbCodeExchangeVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String userIds = list.stream().map(s -> s.getUserId().toString()).collect(Collectors.joining(","));
        userIds = StringUtils.isBlank(userIds)?"0":userIds;
        List<UserVo> userVoList = iUserService.listVo(new LambdaQueryWrapper<User>().inSql(User::getId, userIds));
        list.forEach(item->{
        item.getExtData().put("user", userVoList.stream().filter(s -> s.getId().compareTo(item.getUserId()) == 0).findFirst().orElse(null));
        });
        String codeIds = list.stream().map(s -> s.getCodeId().toString()).collect(Collectors.joining(","));
        codeIds = StringUtils.isBlank(codeIds)?"0":codeIds;
        List<YbVipCodeVo> ybVipCodeVoList = iYbVipCodeService.listVo(new LambdaQueryWrapper<YbVipCode>().inSql(YbVipCode::getId, codeIds));
        list.forEach(item->{
        item.getExtData().put("ybVipCode", ybVipCodeVoList.stream().filter(s -> s.getId().compareTo(item.getCodeId()) == 0).findFirst().orElse(null));
        });

        return list;
    }
}
