package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.VLinkBo;
import com.yibei.yb.domain.vo.VLinkVo;
import com.yibei.yb.domain.VLink;
import com.yibei.yb.mapper.VLinkMapper;
import com.yibei.yb.service.IVLinkService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 相关链接Service业务层处理
 *
 * @author yibei
 * @date 2022-06-09
 */
@Service
public class VLinkServiceImpl extends ServicePlusImpl<VLinkMapper, VLink, VLinkVo> implements IVLinkService {


    @Override
    public VLinkVo queryById(String type){
        return getVoById(type);
    }

    @Override
    public TableDataInfo<VLinkVo> queryPageList(VLinkBo bo) {
        PagePlus<VLink, VLinkVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<VLinkVo> queryList(VLinkBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<VLink> buildQueryWrapper(VLinkBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<VLink> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(VLink::getType);
        lqw.orderByAsc(VLink::getSort);
//        lqw.orderByDesc(VLink::getId);
        lqw.eq(StringUtils.isNotBlank(bo.getType()), VLink::getType, bo.getType());
        lqw.eq(bo.getBookId() != null, VLink::getBookId, bo.getBookId());
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), VLink::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getLabel()), VLink::getLabel, bo.getLabel());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrl()), VLink::getImgUrl, bo.getImgUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getImportance()), VLink::getImportance, bo.getImportance());
        lqw.eq(bo.getSort() != null, VLink::getSort, bo.getSort());
        lqw.like(StringUtils.isNotBlank(bo.getBookTitle()), VLink::getBookTitle, bo.getBookTitle());
        return lqw;
    }

    @Override
    public Boolean insertByBo(VLinkBo bo) {
        VLink add = BeanUtil.toBean(bo, VLink.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(VLinkBo bo) {
        VLink update = BeanUtil.toBean(bo, VLink.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(VLink entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<String> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public VLinkVo queryExtData(VLinkVo vo) {
        List<VLinkVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<VLinkVo> queryExtData(List<VLinkVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }
}
