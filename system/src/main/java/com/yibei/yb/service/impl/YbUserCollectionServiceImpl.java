package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbUserCollectionBo;
import com.yibei.yb.domain.vo.YbUserCollectionVo;
import com.yibei.yb.domain.YbUserCollection;
import com.yibei.yb.mapper.YbUserCollectionMapper;
import com.yibei.yb.service.IYbUserCollectionService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 用户收藏Service业务层处理
 *
 * @author yibei
 * @date 2022-05-06
 */
@Service
public class YbUserCollectionServiceImpl extends ServicePlusImpl<YbUserCollectionMapper, YbUserCollection, YbUserCollectionVo> implements IYbUserCollectionService {


    @Override
    public YbUserCollectionVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbUserCollectionVo> queryPageList(YbUserCollectionBo bo) {
        PagePlus<YbUserCollection, YbUserCollectionVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbUserCollectionVo> queryList(YbUserCollectionBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbUserCollection> buildQueryWrapper(YbUserCollectionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbUserCollection> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbUserCollection::getId);
        lqw.eq(bo.getContentType() != null, YbUserCollection::getContentType, bo.getContentType());
        lqw.eq(bo.getContentId() != null, YbUserCollection::getContentId, bo.getContentId());
        lqw.eq(bo.getEntryId() != null, YbUserCollection::getEntryId, bo.getEntryId());
        lqw.eq(bo.getUserId() != null, YbUserCollection::getUserId, bo.getUserId());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbUserCollectionBo bo) {
        YbUserCollection add = BeanUtil.toBean(bo, YbUserCollection.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbUserCollectionBo bo) {
        YbUserCollection update = BeanUtil.toBean(bo, YbUserCollection.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbUserCollection entity){
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
    public YbUserCollectionVo queryExtData(YbUserCollectionVo vo) {
        List<YbUserCollectionVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbUserCollectionVo> queryExtData(List<YbUserCollectionVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }
}
