package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbStudyConfigBo;
import com.yibei.yb.domain.vo.YbStudyConfigVo;
import com.yibei.yb.domain.YbStudyConfig;
import com.yibei.yb.mapper.YbStudyConfigMapper;
import com.yibei.yb.service.IYbStudyConfigService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 教材学习配置Service业务层处理
 *
 * @author yibei
 * @date 2022-05-12
 */
@Service
public class YbStudyConfigServiceImpl extends ServicePlusImpl<YbStudyConfigMapper, YbStudyConfig, YbStudyConfigVo> implements IYbStudyConfigService {


    @Override
    public YbStudyConfigVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbStudyConfigVo> queryPageList(YbStudyConfigBo bo) {
        PagePlus<YbStudyConfig, YbStudyConfigVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbStudyConfigVo> queryList(YbStudyConfigBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbStudyConfig> buildQueryWrapper(YbStudyConfigBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbStudyConfig> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbStudyConfig::getId);
        lqw.eq(bo.getTeachingMaterialId() != null, YbStudyConfig::getTeachingMaterialId, bo.getTeachingMaterialId());
        lqw.eq(bo.getIsVoice() != null, YbStudyConfig::getIsVoice, bo.getIsVoice());
        lqw.eq(bo.getUserId() != null, YbStudyConfig::getUserId, bo.getUserId());
        lqw.eq(bo.getQuantity() != null, YbStudyConfig::getQuantity, bo.getQuantity());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbStudyConfigBo bo) {
        YbStudyConfig add = BeanUtil.toBean(bo, YbStudyConfig.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbStudyConfigBo bo) {
        YbStudyConfig update = BeanUtil.toBean(bo, YbStudyConfig.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbStudyConfig entity){
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
    public YbStudyConfigVo queryExtData(YbStudyConfigVo vo) {
        List<YbStudyConfigVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbStudyConfigVo> queryExtData(List<YbStudyConfigVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }
}
