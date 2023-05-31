package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
    import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbQuestionBankBo;
import com.yibei.yb.domain.vo.YbQuestionBankVo;
import com.yibei.yb.domain.YbQuestionBank;
import com.yibei.yb.mapper.YbQuestionBankMapper;
import com.yibei.yb.service.IYbQuestionBankService;

import com.yibei.common.utils.StringUtils;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;
import javax.annotation.Resource;

/**
 * 题库Service业务层处理
 *
 * @author yibei
 * @date 2022-05-06
 */
@Service
public class YbQuestionBankServiceImpl extends ServicePlusImpl<YbQuestionBankMapper, YbQuestionBank, YbQuestionBankVo> implements IYbQuestionBankService {


    @Override
    public YbQuestionBankVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbQuestionBankVo> queryPageList(YbQuestionBankBo bo) {
        PagePlus<YbQuestionBank, YbQuestionBankVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbQuestionBankVo> queryList(YbQuestionBankBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbQuestionBank> buildQueryWrapper(YbQuestionBankBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbQuestionBank> lqw = Wrappers.lambdaQuery();
        lqw.orderByAsc(YbQuestionBank::getSort);
        lqw.orderByDesc(YbQuestionBank::getCreateTime);
//        lqw.orderByDesc(YbQuestionBank::getId);
        lqw.like(StringUtils.isNotBlank(bo.getTitle()), YbQuestionBank::getTitle, bo.getTitle());
        lqw.eq(StringUtils.isNotBlank(bo.getImgUrl()), YbQuestionBank::getImgUrl, bo.getImgUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getDescribe()), YbQuestionBank::getDescribe, bo.getDescribe());
        lqw.eq(bo.getSort() != null, YbQuestionBank::getSort, bo.getSort());
        lqw.eq(bo.getStatus() != null, YbQuestionBank::getStatus, bo.getStatus());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbQuestionBankBo bo) {
        YbQuestionBank add = BeanUtil.toBean(bo, YbQuestionBank.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbQuestionBankBo bo) {
        YbQuestionBank update = BeanUtil.toBean(bo, YbQuestionBank.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbQuestionBank entity){
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
    public YbQuestionBankVo queryExtData(YbQuestionBankVo vo) {
        List<YbQuestionBankVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbQuestionBankVo> queryExtData(List<YbQuestionBankVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }


        return list;
    }
}
