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
import com.yibei.yb.domain.bo.YbOrderPayBo;
import com.yibei.yb.domain.vo.YbOrderPayVo;
import com.yibei.yb.domain.YbOrderPay;
import com.yibei.yb.mapper.YbOrderPayMapper;
import com.yibei.yb.service.IYbOrderPayService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 支付记录Service业务层处理
 *
 * @author yibei
 * @date 2022-04-27
 */
@Service
public class YbOrderPayServiceImpl extends ServicePlusImpl<YbOrderPayMapper, YbOrderPay, YbOrderPayVo> implements IYbOrderPayService {

    @Resource
    private IUserService iUserService;

    @Override
    public YbOrderPayVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbOrderPayVo> queryPageList(YbOrderPayBo bo) {
        PagePlus<YbOrderPay, YbOrderPayVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbOrderPayVo> queryList(YbOrderPayBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbOrderPay> buildQueryWrapper(YbOrderPayBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbOrderPay> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbOrderPay::getCreateTime);
//        lqw.orderByDesc(YbOrderPay::getId);
        lqw.eq(bo.getUserId() != null, YbOrderPay::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderNo()), YbOrderPay::getOrderNo, bo.getOrderNo());
        lqw.eq(bo.getPayTime() != null, YbOrderPay::getPayTime, bo.getPayTime());
        lqw.eq(bo.getPayAmount() != null, YbOrderPay::getPayAmount, bo.getPayAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getTradeNo()), YbOrderPay::getTradeNo, bo.getTradeNo());
        lqw.eq(StringUtils.isNotBlank(bo.getResponseData()), YbOrderPay::getResponseData, bo.getResponseData());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbOrderPayBo bo) {
        YbOrderPay add = BeanUtil.toBean(bo, YbOrderPay.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbOrderPayBo bo) {
        YbOrderPay update = BeanUtil.toBean(bo, YbOrderPay.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbOrderPay entity){
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
    public YbOrderPayVo queryExtData(YbOrderPayVo vo) {
        List<YbOrderPayVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbOrderPayVo> queryExtData(List<YbOrderPayVo> list){
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
