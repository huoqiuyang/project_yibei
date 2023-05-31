package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.yb.domain.clientVo.ReportSigninInfoVo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbSigninBo;
import com.yibei.yb.domain.vo.YbSigninVo;
import com.yibei.yb.domain.YbSignin;
import com.yibei.yb.mapper.YbSigninMapper;
import com.yibei.yb.service.IYbSigninService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 签到Service业务层处理
 *
 * @author yibei
 * @date 2022-05-18
 */
@Service
public class YbSigninServiceImpl extends ServicePlusImpl<YbSigninMapper, YbSignin, YbSigninVo> implements IYbSigninService {


    @Override
    public YbSigninVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbSigninVo> queryPageList(YbSigninBo bo) {
        PagePlus<YbSignin, YbSigninVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbSigninVo> queryList(YbSigninBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbSignin> buildQueryWrapper(YbSigninBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbSignin> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbSignin::getId);
        lqw.eq(bo.getUserId() != null, YbSignin::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getDateDay()), YbSignin::getDateDay, bo.getDateDay());
        lqw.eq(bo.getContinuityDay() != null, YbSignin::getContinuityDay, bo.getContinuityDay());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbSigninBo bo) {
        YbSignin add = BeanUtil.toBean(bo, YbSignin.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbSigninBo bo) {
        YbSignin update = BeanUtil.toBean(bo, YbSignin.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbSignin entity){
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
    public YbSigninVo queryExtData(YbSigninVo vo) {
        List<YbSigninVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbSigninVo> queryExtData(List<YbSigninVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }

    @Override
    public List<ReportSigninInfoVo> getReportSignin(Long userId, String month) {
        String monthDay = month+"-01";
        return baseMapper.getReportSignin(userId,monthDay);
    }
}
