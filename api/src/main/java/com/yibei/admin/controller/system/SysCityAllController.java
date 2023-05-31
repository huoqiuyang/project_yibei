package com.yibei.admin.controller.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import com.yibei.common.core.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.PageData;
import com.yibei.framework.security.AllowAnonymous;
import com.yibei.system.domain.CityInfo;
import com.yibei.system.domain.CityRegion;
import com.yibei.system.domain.bo.IdVo;
import com.yibei.system.domain.bo.ParentIdBo;
import com.yibei.system.domain.bo.TreeDataVo;
import com.yibei.system.service.ICityInfoService;
import com.yibei.system.service.ICityRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(tags = "全国城市模块")
@RequestMapping("/admin/system/sysCityAll")
public class SysCityAllController extends BaseController {


	@Autowired
	private ICityInfoService iCityInfoService;
	@Autowired
	private ICityRegionService iCityRegionService;


	//格式化数据
	private HashMap<String,Object> getFormatObj(CityInfo cityInfo){
		HashMap<String, Object> map = Maps.newHashMap();
		map.put("id",cityInfo.getId());
		map.put("parentId",cityInfo.getParentId());
		map.put("name",cityInfo.getName());
		return map;
	}
	//递归获取无限级列表
	private List<HashMap<String,Object>> getSubListByPid(Integer parentId, List<CityInfo> list, String childrenKey){
		List<CityInfo> collect = list.parallelStream().filter(c -> c.getParentId().compareTo(parentId)==0).collect(Collectors.toList());
		List<HashMap<String,Object>> treeList=new ArrayList<>();
		for (CityInfo item :collect) {
			HashMap<String, Object> objectObjectHashMap=getFormatObj(item);
			if (list.parallelStream().anyMatch(c->c.getParentId().compareTo(item.getId())==0)){
				objectObjectHashMap.put(childrenKey,getSubListByPid(item.getId(),list,childrenKey));
			}
			treeList.add(objectObjectHashMap);
		}
		return treeList;
	}

	@ApiOperation("获取所有省份")
	@PostMapping("/getProvinces")
	@AllowAnonymous
	public AjaxResult getProvinces(){
		LambdaQueryWrapper<CityInfo> cityInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
		cityInfoLambdaQueryWrapper.orderByAsc(CityInfo::getId);
		cityInfoLambdaQueryWrapper.eq(CityInfo::getParentId,0);
		cityInfoLambdaQueryWrapper.eq(CityInfo::getIsDeleted,0);
		List<CityInfo> list = iCityInfoService.list(cityInfoLambdaQueryWrapper);
		return AjaxResult.success(list.parallelStream().map(c->getFormatObj(c)));
	}

	@ApiOperation("根据parentId获取下级列表")
	@PostMapping("/getChildrens")
	@AllowAnonymous
	public AjaxResult getChildrens(@RequestBody ParentIdBo vo){
		LambdaQueryWrapper<CityInfo> cityInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
		cityInfoLambdaQueryWrapper.orderByAsc(CityInfo::getId);
		cityInfoLambdaQueryWrapper.eq(CityInfo::getParentId,vo.getParentId());
		cityInfoLambdaQueryWrapper.eq(CityInfo::getIsDeleted,0);
		List<CityInfo> list = iCityInfoService.list(cityInfoLambdaQueryWrapper);
		return AjaxResult.success(list.parallelStream().map(c->getFormatObj(c)));
	}

	@ApiOperation("根据cityId获取街道列表")
	@PostMapping("/getRegionByCityId")
	@AllowAnonymous
	public AjaxResult getChildrens(@RequestBody IdVo vo){
		LambdaQueryWrapper<CityRegion> lambdaQueryWrapper = new LambdaQueryWrapper<>();
		lambdaQueryWrapper.orderByAsc(CityRegion::getSort).orderByAsc(CityRegion::getId);
		lambdaQueryWrapper.eq(CityRegion::getCityId,vo.getId());
		lambdaQueryWrapper.eq(CityRegion::getIsDeleted,0);
		List<CityRegion> list = iCityRegionService.list(lambdaQueryWrapper);
		return AjaxResult.success(list.parallelStream().map(c->{
			HashMap<String, Object> map = Maps.newHashMap();
			map.put("id",c.getId());
			map.put("cityId",c.getCityId());
			map.put("name",c.getName());
			return map;
		}));
	}

	@ApiOperation("获取树形结构省市区列表")
	@PostMapping("/getTreeList")
	@AllowAnonymous
	public AjaxResult getTreeList(@RequestBody TreeDataVo vo){
		LambdaQueryWrapper<CityInfo> cityInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
		cityInfoLambdaQueryWrapper.orderByAsc(CityInfo::getId)
			.eq(CityInfo::getIsDeleted,0)
			.and(vo.getParentId()>0,c->c.eq(CityInfo::getId,vo.getParentId()).eq(CityInfo::getParentId,0))
			.or(vo.getParentId()>0,c->c.ne(CityInfo::getParentId,0));
		List<CityInfo> list = iCityInfoService.list(cityInfoLambdaQueryWrapper);
		List<HashMap<String,Object>> treeList=getSubListByPid(0,list,vo.getChildrenKey());
//        if (vo.getParentId()>0&&treeList.size()==1){
//            return ResultVo.createBySuccessData(treeList.get(0));
//        }
		return AjaxResult.success(treeList);
	}


	@ApiOperation("获取省市区联动首字母排序列表")
	@PostMapping("/getMultiProvincesList")
	@AllowAnonymous
	public AjaxResult getMultiProvincesList(@RequestBody @Validated ParentIdBo vo){

		List<PageData> list = iCityInfoService.getMultilevelSortList(vo);
		if(vo.getParentId()>376){
			list = iCityInfoService.getMultilevelSortStreetList(vo);
		}
		list.forEach(item->{
			if(item.getInt("id").equals(23)||item.getInt("id").equals(271)){
				item.put("letter", "C");
			}
		});

		String[] indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
		List<String> letterList = new ArrayList();
		List<PageData> customerData = new ArrayList();
		List<PageData> customerList;

		PageData pd;
		PageData _pd;
		for (String letter : indexList) {
			pd = new PageData();
			pd.put("letter", letter);
			customerList = new ArrayList();
			for (PageData level : list) {
				if(level.getString("letter").equals(letter)){
					_pd = new PageData();
					_pd.put("id", level.get("id"));
					_pd.put("name", level.getString("name"));
					customerList.add(_pd);
				}
			}
			pd.put("customerList", customerList);
			if(customerList.size()>0){
				customerData.add(pd);
				letterList.add(letter);
			}
		}

		PageData pd_ = new PageData();
		pd_.put("indexList", letterList);
		pd_.put("customerData", customerData);
		return AjaxResult.success(pd_);
	}


	@ApiOperation("获取全部市首字母排序列表")
	@PostMapping("/cityUrbanList")
	@AllowAnonymous
	public AjaxResult cityUrbanList(){

		List<PageData> list = iCityInfoService.cityUrbanList();
		list.forEach(item->{
			if(item.getInt("id").equals(23)||item.getInt("id").equals(271)){
				item.put("letter", "C");
			}
		});

		String[] indexList = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");
		List<String> letterList = new ArrayList();
		List<PageData> customerData = new ArrayList();
		List<PageData> customerList;

		PageData pd;
		PageData _pd;
		for (String letter : indexList) {
			pd = new PageData();
			pd.put("letter", letter);
			customerList = new ArrayList();
			for (PageData level : list) {
				if(level.getString("letter").equals(letter)){
					_pd = new PageData();
					_pd.put("id", level.get("id"));
					_pd.put("name", level.getString("name"));
					customerList.add(_pd);
				}
			}
			pd.put("customerList", customerList);
			if(customerList.size()>0){
				customerData.add(pd);
				letterList.add(letter);
			}
		}

		PageData pd_ = new PageData();
		pd_.put("indexList", letterList);
		pd_.put("customerData", customerData);
		return AjaxResult.success(pd_);
	}
}
