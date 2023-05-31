package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.TimeUtils;
import com.yibei.system.domain.clientBo.PageBo;
import com.yibei.yb.domain.clientVo.YbUserBookshelfInfoVo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbUserBookshelfBo;
import com.yibei.yb.domain.vo.YbUserBookshelfVo;
import com.yibei.yb.domain.YbUserBookshelf;
import com.yibei.yb.mapper.YbUserBookshelfMapper;
import com.yibei.yb.service.IYbUserBookshelfService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 用户书架Service业务层处理
 *
 * @author yibei
 * @date 2022-05-06
 */
@Service
public class YbUserBookshelfServiceImpl extends ServicePlusImpl<YbUserBookshelfMapper, YbUserBookshelf, YbUserBookshelfVo> implements IYbUserBookshelfService {


    @Override
    public YbUserBookshelfVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbUserBookshelfVo> queryPageList(YbUserBookshelfBo bo) {
        PagePlus<YbUserBookshelf, YbUserBookshelfVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbUserBookshelfVo> queryList(YbUserBookshelfBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbUserBookshelf> buildQueryWrapper(YbUserBookshelfBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbUserBookshelf> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbUserBookshelf::getId);
        lqw.eq(bo.getContentType() != null, YbUserBookshelf::getContentType, bo.getContentType());
        lqw.eq(bo.getContentId() != null, YbUserBookshelf::getContentId, bo.getContentId());
        lqw.eq(bo.getUserId() != null, YbUserBookshelf::getUserId, bo.getUserId());
        lqw.eq(bo.getLastOpenTime() != null, YbUserBookshelf::getLastOpenTime, bo.getLastOpenTime());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbUserBookshelfBo bo) {
        YbUserBookshelf add = BeanUtil.toBean(bo, YbUserBookshelf.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbUserBookshelfBo bo) {
        YbUserBookshelf update = BeanUtil.toBean(bo, YbUserBookshelf.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbUserBookshelf entity){
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
    public YbUserBookshelfVo queryExtData(YbUserBookshelfVo vo) {
        List<YbUserBookshelfVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbUserBookshelfVo> queryExtData(List<YbUserBookshelfVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }

    @Override
    public YbUserBookshelf getUserBookshelf(Integer contentType, Long contentId, Long userId) {
        YbUserBookshelf userBookshelf = null;
        if(userId!=null && !userId.equals(0l)){
            userBookshelf = getOne(new LambdaQueryWrapper<YbUserBookshelf>()
                    .eq(YbUserBookshelf::getContentType,contentType)
                    .eq(YbUserBookshelf::getContentId,contentId)
                    .eq(YbUserBookshelf::getUserId,userId)
                    .last("LIMIT 1"));
        }
        return userBookshelf;
    }

    @Override
    public void updateLastTime(Long userId, Long contentType, Long contentId) {
        YbUserBookshelf userBookshelf = getOne(new LambdaQueryWrapper<YbUserBookshelf>()
                .eq(YbUserBookshelf::getUserId,userId)
                .eq(YbUserBookshelf::getContentType,contentType)
                .eq(YbUserBookshelf::getContentId,contentId)
                .last("LIMIT 1"));
        if(userBookshelf!=null){
            userBookshelf.setLastOpenTime(TimeUtils.getCurrentDate());
            updateById(userBookshelf);
        }
    }

    @Override
    public Page<YbUserBookshelfInfoVo> getListInfo(PageBo bo, Long userId) {
        return baseMapper.getListInfo(new Page(bo.getPageNum(),bo.getPageSize()),userId);
    }

    @Override
    public long getIsBookshelf(Long userId, Long contentType, Long contentId) {
        long isBookshelf = count(new LambdaQueryWrapper<YbUserBookshelf>()
                .eq(YbUserBookshelf::getUserId,userId)
                .eq(YbUserBookshelf::getContentType,contentType)
                .eq(YbUserBookshelf::getContentId,contentId));
        return isBookshelf>0?1:0;
    }
}
