package com.yibei.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yibei.common.core.page.PagePlus;
import com.yibei.common.core.page.TableDataInfo;
import com.yibei.common.utils.PageData;
import com.yibei.common.utils.PageUtils;
import com.yibei.system.domain.CityInfo;
import com.yibei.system.domain.bo.CityInfoAddBo;
import com.yibei.system.domain.bo.CityInfoEditBo;
import com.yibei.system.domain.bo.CityInfoQueryBo;
import com.yibei.system.domain.bo.ParentIdBo;
import com.yibei.system.domain.vo.AMapCityInfoVo;
import com.yibei.system.domain.vo.CityInfoVo;
import com.yibei.system.mapper.CityInfoMapper;
import com.yibei.system.service.ICityInfoService;
import jodd.util.StringUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author frame
 * @date 2021-07-14
 */
@Service
public class CityInfoServiceImpl extends ServiceImpl<CityInfoMapper, CityInfo> implements ICityInfoService {

    @Resource
    private CityInfoMapper cityInfoMapper;

    @Value("${amap.key}")
    private String key;

    @Override
    public CityInfoVo queryById(Integer id){
        return getVoById(id, CityInfoVo.class);
    }

    @Override
    public TableDataInfo<CityInfoVo> queryPageList(CityInfoQueryBo bo) {
        PagePlus<CityInfo, CityInfoVo> result = pageVo(PageUtils.buildPagePlus(), buildQueryWrapper(bo), CityInfoVo.class);
        return PageUtils.buildDataInfo(result);
    }

    @Override
    public List<CityInfoVo> queryList(CityInfoQueryBo bo) {
        return listVo(buildQueryWrapper(bo), CityInfoVo.class);
    }

    private LambdaQueryWrapper<CityInfo> buildQueryWrapper(CityInfoQueryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CityInfo> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, CityInfo::getParentId, bo.getParentId());
        lqw.like(StrUtil.isNotBlank(bo.getName()), CityInfo::getName, bo.getName());
        lqw.eq(StrUtil.isNotBlank(bo.getZipcode()), CityInfo::getZipcode, bo.getZipcode());
        lqw.eq(bo.getRegionLevel() != null, CityInfo::getRegionLevel, bo.getRegionLevel());
        lqw.eq(bo.getState() != null, CityInfo::getState, bo.getState());
        lqw.eq(bo.getIsDeleted() != null, CityInfo::getIsDeleted, bo.getIsDeleted());
        return lqw;
    }

    @Override
    public Boolean insertByAddBo(CityInfoAddBo bo) {
        CityInfo add = BeanUtil.toBean(bo, CityInfo.class);
        validEntityBeforeSave(add);
        return save(add);
    }

    @Override
    public Boolean updateByEditBo(CityInfoEditBo bo) {
        CityInfo update = BeanUtil.toBean(bo, CityInfo.class);
        validEntityBeforeSave(update);
        return updateById(update);
    }

    /**
     * 保存前的数据校验
     *
     * @param entity 实体类数据
     */
    private void validEntityBeforeSave(CityInfo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Integer> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return removeByIds(ids);
    }

    @Override
    public List<PageData> getMultilevelSortList(ParentIdBo vo) {
        return baseMapper.getMultilevelSortList(vo);
    }

    @Override
    public List<PageData> getMultilevelSortStreetList(ParentIdBo vo) {
        return baseMapper.getMultilevelSortStreetList(vo);
    }

    @Override
    public List<PageData> cityUrbanList() {
        return baseMapper.cityUrbanList();
    }

    @Override
    public AMapCityInfoVo getCityInfoByLocation(String lon, String lat) {
        if (Strings.isBlank(lon)||Strings.isBlank(lat)){
            return null;
        }

        String URL = "https://restapi.amap.com/v3/geocode/regeo?location="+lon+","+lat+"&key=" + key;
        String result = HttpRequest.get(URL)
                .timeout(20000)
                .execute().body();
        JSONObject jsonObject = JSON.parseObject(result);

        AMapCityInfoVo cityInfoVo = new AMapCityInfoVo();
        if (!jsonObject.getString("status").equals("1")){
            return null;
        }
        JSONObject regeocode= jsonObject.getJSONObject("regeocode");
        JSONObject addressComponent=regeocode.getJSONObject("addressComponent");
        cityInfoVo.setProvince(addressComponent.getString("province"));
        cityInfoVo.setCity(addressComponent.getString("city"));
        cityInfoVo.setDistrict(addressComponent.getString("district"));
        cityInfoVo.setFormatAddress(regeocode.getString("formatted_address"));
        //判断是否是直辖市
        if (cityInfoVo.getCity().equals("[]")){
            cityInfoVo.setCity(cityInfoVo.getProvince().substring(0,2));
        }
        //匹配数据库id
        List<CityInfo> selectList = baseMapper.selectList(new QueryWrapper<CityInfo>().select("id", "name","region_level","parent_id").lambda().likeRight(CityInfo::getName, cityInfoVo.getProvince().substring(0,2)).or().likeRight(CityInfo::getName, cityInfoVo.getCity().substring(0,2)).or().likeRight(CityInfo::getName, cityInfoVo.getDistrict().substring(0,2)).orderByAsc(CityInfo::getParentId));
        String provinceIds = "";
        for (CityInfo item :selectList) {
            if (item.getRegionLevel()==1 && item.getParentId()==0){
                cityInfoVo.setProvince(item.getName());
                cityInfoVo.setProvinceId(item.getId());
            }
            if (item.getRegionLevel()==2 && !isEmpty(cityInfoVo.getProvinceId()) && item.getParentId().equals(cityInfoVo.getProvinceId())){
                cityInfoVo.setCity(item.getName());
                cityInfoVo.setCityId(item.getId());
            }
            if (item.getRegionLevel()==3 && !isEmpty(cityInfoVo.getCityId()) && item.getParentId().toString().equals(cityInfoVo.getCityId().toString())){
                cityInfoVo.setDistrict(item.getName());
                cityInfoVo.setDistrictId(item.getId());
            }
            if(item.getRegionLevel()==3){
                if(provinceIds.length()<1){
                    provinceIds = provinceIds + item.getParentId() + "," + item.getId();
                }else{
                    provinceIds = provinceIds + "," + item.getParentId() + "," + item.getId();
                }
            }
        }
        if(isEmpty(cityInfoVo.getCityId()) || isEmpty(cityInfoVo.getDistrictId())){
            List<CityInfo> selectLists = baseMapper.selectList(new QueryWrapper<CityInfo>().select("id", "name","region_level","parent_id").lambda().in(CityInfo::getId,provinceIds.split(",")).orderByAsc(CityInfo::getParentId));
            for (CityInfo item :selectLists){
                if (item.getRegionLevel()==2 && !isEmpty(cityInfoVo.getProvinceId()) && item.getParentId().equals(cityInfoVo.getProvinceId())){
                    cityInfoVo.setCity(item.getName());
                    cityInfoVo.setCityId(item.getId());
                }
                if (item.getRegionLevel()==3 && !isEmpty(cityInfoVo.getCityId()) && item.getParentId().toString().equals(cityInfoVo.getCityId().toString())){
                    cityInfoVo.setDistrict(item.getName());
                    cityInfoVo.setDistrictId(item.getId());
                }
            }
        }
        return cityInfoVo;
    }
}
