package com.yibei.yb.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.yibei.common.utils.PageUtils;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.yb.domain.*;
import com.yibei.yb.domain.vo.YbExpandReadingVo;
import com.yibei.yb.domain.vo.YbQuestionBankContentVo;
import com.yibei.yb.domain.vo.YbTeachingMaterialEntryVo;
import com.yibei.yb.service.*;
import org.springframework.stereotype.Service;
import com.yibei.common.core.mybatisplus.core.ServicePlusImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yibei.yb.domain.bo.YbContentLinkBo;
import com.yibei.yb.domain.vo.YbContentLinkVo;
import com.yibei.yb.mapper.YbContentLinkMapper;

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
 * @date 2022-05-06
 */
@Service
public class YbContentLinkServiceImpl extends ServicePlusImpl<YbContentLinkMapper, YbContentLink, YbContentLinkVo> implements IYbContentLinkService {

    @Resource
    private IYbTeachingMaterialEntryService iYbTeachingMaterialEntryService;

    @Resource
    private IYbTeachingMaterialService iYbTeachingMaterialService;

    @Resource
    private IYbQuestionBankContentService iYbQuestionBankContentService;

    @Resource
    private IYbQuestionBankService iYbQuestionBankService;

    @Resource
    private IYbExpandReadingService iYbExpandReadingService;

    @Override
    public YbContentLinkVo queryById(Long id){
        return getVoById(id);
    }

    @Override
    public TableDataInfo<YbContentLinkVo> queryPageList(YbContentLinkBo bo) {
        PagePlus<YbContentLink, YbContentLinkVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo));
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<YbContentLinkVo> queryList(YbContentLinkBo bo) {
        return listVo(buildQueryWrapper(bo));
    }

    private LambdaQueryWrapper<YbContentLink> buildQueryWrapper(YbContentLinkBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<YbContentLink> lqw = Wrappers.lambdaQuery();
        lqw.orderByDesc(YbContentLink::getCreateTime);
//        lqw.orderByDesc(YbContentLink::getId);
        lqw.eq(bo.getSourceType() != null, YbContentLink::getSourceType, bo.getSourceType());
        lqw.eq(bo.getEntryId() != null, YbContentLink::getEntryId, bo.getEntryId());
        lqw.eq(bo.getLinkSourceType() != null, YbContentLink::getLinkSourceType, bo.getLinkSourceType());
        lqw.eq(bo.getLinkEntryId() != null, YbContentLink::getLinkEntryId, bo.getLinkEntryId());
        return lqw;
    }

    @Override
    public Boolean insertByBo(YbContentLinkBo bo) {
        YbContentLink add = BeanUtil.toBean(bo, YbContentLink.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByBo(YbContentLinkBo bo) {
        YbContentLink update = BeanUtil.toBean(bo, YbContentLink.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(YbContentLink entity){
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
    public YbContentLinkVo queryExtData(YbContentLinkVo vo) {
        List<YbContentLinkVo> list = new ArrayList<>();
        list.add(vo);
        return queryExtData(list).get(0);
    }

    @Override
    public List<YbContentLinkVo> queryExtData(List<YbContentLinkVo> list){
        if(list ==null || list.size() == 0){
            return list;
        }

        String linkEntryIds = list.stream().map(s -> s.getLinkEntryId().toString()).collect(Collectors.joining(","));
        linkEntryIds = StringUtils.isBlank(linkEntryIds)?"0":linkEntryIds;
        List<YbTeachingMaterialEntryVo> ybTeachingMaterialEntryVoList = iYbTeachingMaterialEntryService.listVo(new LambdaQueryWrapper<YbTeachingMaterialEntry>().inSql(YbTeachingMaterialEntry::getId, linkEntryIds));

        List<YbQuestionBankContentVo> ybQuestionBankContentVoList = iYbQuestionBankContentService.listVo(new LambdaQueryWrapper<YbQuestionBankContent>().inSql(YbQuestionBankContent::getId, linkEntryIds));

        List<YbExpandReadingVo> ybExpandReadingList = iYbExpandReadingService.listVo(new LambdaQueryWrapper<YbExpandReading>().inSql(YbExpandReading::getId, linkEntryIds));

        list.forEach(item->{
            Long linkSourceType = item.getLinkSourceType();
            if(linkSourceType.equals(1l)){
                //教材
                YbTeachingMaterialEntryVo ybTeachingMaterialEntryVo = ybTeachingMaterialEntryVoList.stream().filter(s -> s.getId().compareTo(item.getLinkEntryId()) == 0).findFirst().orElse(null);
                if(ybTeachingMaterialEntryVo!=null){
                    item.getExtData().put("entry", ybTeachingMaterialEntryVo);
                    item.getExtData().put("title", getSourceTitle(1,ybTeachingMaterialEntryVo.getTeachingMaterialId()));
                }else{
                    item.getExtData().put("entry", null);
                    item.getExtData().put("title", "");
                }
            }else if(linkSourceType.equals(2l)){
                //题库
                YbQuestionBankContentVo ybQuestionBankContentVo = ybQuestionBankContentVoList.stream().filter(s -> s.getId().compareTo(item.getLinkEntryId()) == 0).findFirst().orElse(null);
                if(ybQuestionBankContentVo!=null){
                    item.getExtData().put("entry", ybQuestionBankContentVo);
                    item.getExtData().put("title", getSourceTitle(2,ybQuestionBankContentVo.getQuestionBankId()));
                }else{
                    item.getExtData().put("entry", null);
                    item.getExtData().put("title", "");
                }
            }else if(linkSourceType.equals(3l)){
                //拓展阅读
                YbExpandReadingVo ybExpandReadingVo = ybExpandReadingList.stream().filter(s -> s.getId().compareTo(item.getLinkEntryId()) == 0).findFirst().orElse(null);
                if(ybExpandReadingVo!=null){
                    item.getExtData().put("entry", ybExpandReadingVo);
                    item.getExtData().put("title", ybExpandReadingVo.getTitle());
                }else{
                    item.getExtData().put("entry", null);
                    item.getExtData().put("title", "");
                }
            }
        });

        return list;
    }

    /**
     * 获取词条对应的 教材/题库
     * */
    private String getSourceTitle(int type,Long id){
        String title = "";
        if(type==1){
            //教材
            YbTeachingMaterial ybTeachingMaterial = iYbTeachingMaterialService.getById(id);
            if(ybTeachingMaterial!=null){
                title = ybTeachingMaterial.getTitle();
            }
        }else if(type==2){
            //题库
            YbQuestionBank ybQuestionBank = iYbQuestionBankService.getById(id);
            if(ybQuestionBank!=null){
                title = ybQuestionBank.getTitle();
            }
        }

        return title;
    }
}
