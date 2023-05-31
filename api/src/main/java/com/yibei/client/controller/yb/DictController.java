package com.yibei.client.controller.yb;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.core.domain.entity.SysDictData;
import com.yibei.common.core.domain.entity.SysDictType;
import com.yibei.framework.security.AllowAnonymous;
import com.yibei.system.domain.bo.DictTypeBo;
import com.yibei.system.domain.bo.DictTypeListBo;
import com.yibei.system.service.ISysDictDataService;
import com.yibei.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("Client_DictController")
@Api(value = "字典模块",tags = "字典模块")
@RequestMapping("/client/dict")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DictController extends BaseController {

    private final ISysDictTypeService iSysDictTypeService;
    private final ISysDictDataService iSysDictDataService;


    @ApiOperation("字典类别列表")
    @PostMapping("/dictTypeList")
    @AllowAnonymous
    @Cacheable(cacheNames="sysCache",key="'dictTypeList'")
    public AjaxResult<List<SysDictType>> dictTypeList() {

        List<SysDictType> list = iSysDictTypeService.list(new LambdaQueryWrapper<SysDictType>().eq(SysDictType::getStatus,0));
        return AjaxResult.success(list);
    }

    @ApiOperation("字典数据列表")
    @PostMapping("/dictDataList")
    @AllowAnonymous
    @Cacheable(cacheNames="sysCache",key="'dictDataList_'+#bo.dictType")
    public AjaxResult<List<SysDictData>> dictDataList(@RequestBody @Validated DictTypeBo bo) {

        List<SysDictData> list = iSysDictDataService.list(new LambdaQueryWrapper<SysDictData>()
                .eq(SysDictData::getDictType,bo.getDictType())
                .eq(SysDictData::getStatus,0));
        return AjaxResult.success(list);
    }

    @ApiOperation("字典数据列表")
    @PostMapping("/dictDataListByTypes")
    @AllowAnonymous
    public AjaxResult dictDataListByTypes(@RequestBody @Validated DictTypeListBo bo) {
        String[] types = bo.getDictTypes().split(",");
        List<SysDictData> list = iSysDictDataService.listAll();

        Map<String,List<SysDictData>> result = new HashMap<>();
        for (String item : types){
            List<SysDictData> collect = list.stream().filter(s -> s.getDictType().equals(item)).collect(Collectors.toList());
            result.put(item,collect);
        }

        return AjaxResult.success(result);
    }
}
