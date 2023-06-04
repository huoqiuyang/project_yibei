package com.yibei.client.controller.yb;

import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yibei.client.controller.BaseController;
import com.yibei.common.core.domain.AjaxResult;
import com.yibei.common.utils.BeanCopyUtils;
import com.yibei.common.utils.StringUtils;
import com.yibei.yb.domain.YbTeachingMaterialEntry;
import com.yibei.yb.domain.clientBo.PlayListBO;
import com.yibei.yb.domain.clientVo.YbTmeCatalogueInfoVo;
import com.yibei.yb.domain.clientVo.YbTmeCatalogueSecondInfoVo;
import com.yibei.yb.domain.vo.ListenBookItemVO;
import com.yibei.yb.mapper.ListenBookMapper;
import com.yibei.yb.service.IYbTeachingMaterialEntryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController("Client_ListenBookController")
@Api(value = "听书模块",tags = "听书模块")
@RequestMapping("/client/listenBook")
@Slf4j
public class ListenBookController extends BaseController {

    @Resource
    private ListenBookMapper listenBookMapper;

    @Resource
    private IYbTeachingMaterialEntryService iYbTeachingMaterialEntryService;

    @GetMapping("/bookList")
    @ApiOperation("听书-书籍列表")
    public AjaxResult bookList(){
        Long userId = getUserId();
        // 查出教材列表，并关联当前用户的进度
        List<ListenBookItemVO> searchList = listenBookMapper.bookList(userId);
        // 查出有音频上传的教材列表
        List<Long> audioMaterialList  = listenBookMapper.searchAudioMater();
        if (audioMaterialList.size() == 0) {
            return AjaxResult.error("系统中没有已添加音频的教材");
        }
        // 必须是audioMaterialList中存在的教材
        List<ListenBookItemVO> resList = searchList.stream()
                .filter(e -> audioMaterialList.contains(e.getMaterialId())).collect(Collectors.toList());

        return AjaxResult.success(resList);
    }

    @ApiOperation("听书-制定播单列表")
    @GetMapping("/searchPlayList")
    public AjaxResult searchPlayList(@RequestParam @NotNull @ApiParam("教材id") Long materialId){

        Long userId = getUserId();

        // 获取一级菜单
        List<YbTeachingMaterialEntry> list = iYbTeachingMaterialEntryService.list(new LambdaQueryWrapper<YbTeachingMaterialEntry>()
                .eq(YbTeachingMaterialEntry::getTeachingMaterialId,materialId)
                .eq(YbTeachingMaterialEntry::getStatus,1)
                .eq(YbTeachingMaterialEntry::getParentId,0)
                .eq(YbTeachingMaterialEntry::getIsDeleted, 0)
                .orderByAsc(YbTeachingMaterialEntry::getSort).orderByDesc(YbTeachingMaterialEntry::getCreateTime));
        List<YbTmeCatalogueInfoVo> listVo = BeanCopyUtils.listCopy(list,new CopyOptions(), YbTmeCatalogueInfoVo.class);
        if (listVo == null || listVo.size() == 0) {
            log.error("教材id【{}】不存在", materialId);
            return AjaxResult.error("教材不存在");
        }
        // 设置二级标题
        for (YbTmeCatalogueInfoVo item : listVo) {
            List<YbTmeCatalogueSecondInfoVo> secondList = listenBookMapper.searchSecondList(item.getId(), userId);
            item.setSecondList(secondList);
        }

        return AjaxResult.success(listVo);

    }

    @ApiOperation("听书-保存播单")
    @PostMapping("/savePlayList")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult savePlayList(@Valid @RequestBody PlayListBO playListBO) {
        Long userId = getUserId();
        if (playListBO.getEntryList() == null || playListBO.getEntryList().size() == 0){
            return AjaxResult.error("传入的词条列表为空");
        }

        // 设置顺序
        int index = 1;
        for (YbTmeCatalogueSecondInfoVo item : playListBO.getEntryList()) {
            item.setOrder(index);
            index ++;
        }
        // 清除之前的播单
        listenBookMapper.clearPlayList(userId, playListBO.getMaterialId());
        if (listenBookMapper.savePlayList(userId, playListBO.getMaterialId(), playListBO.getEntryList()) == 0) {
            return AjaxResult.error("保存失败");
        }
        return AjaxResult.success("添加成功");
    }

    @ApiOperation("词条轮播图查询")
    @GetMapping("/entryPictures")
    public AjaxResult<List<String>> entryPictures(@RequestParam @NotNull @ApiParam("词条Id") Long entryId){
        String pictureUrls =  listenBookMapper.searchPictures(entryId);
        if (StringUtils.isNotBlank(pictureUrls)) {
            return AjaxResult.success("无图片", null);
        }
        List<String> res = Arrays.asList(pictureUrls.split(","));
        return AjaxResult.success(res);
    }
}
