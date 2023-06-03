package com.yibei.yb.service.impl;

import com.yibei.common.core.domain.AjaxResult;
import com.yibei.mapper.SearchMapper;
import com.yibei.yb.service.SearchService;
import com.yibei.yb.domain.dto.ExpandingItem;
import com.yibei.yb.domain.dto.MaterialItem;
import com.yibei.yb.domain.dto.TopicItemDTO;
import com.yibei.yb.domain.vo.SearchResVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 　 功能描述
 *
 * 　 <p>
 * 　 -----------------------------------------------------------------------------
 * 　 <p>
 * 　 工程名 ： yibei
 * 　 <p>
 * 　 授权 : (C) Copyright topwalk Corporation 2014-2023
 * 　 <p>
 * 　 公司 : 托尔思天行网安信息技术有限责任公司
 * 　 <p>
 * 　 ------------------------------------------------------------------- ----------
 * 　 <p>
 * 　 <font color="#FF0000">注意: 本内容仅限于拓尔思天行网安公司内部使用，禁止转发</font>
 * 　 <p>
 *
 * 　 @version 1.0
 * 　 @author huoqy
 * 　 @createDate 2023年06月01日 20:17
 * 　 @since JDK1.8
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private SearchMapper searchMapper;

    @Override
    public AjaxResult keywordSearch(String keyword) {

        SearchResVO resVO = new SearchResVO();
        List<MaterialItem> materialItems = getMaterial(keyword);
        List<ExpandingItem> expandingItems = getExpanding(keyword);
        List<TopicItemDTO> topicItems = getTopic(keyword);
        
        // 对查询的结果进行处理
        

        resVO.setMaterialList(materialItems);
        resVO.setExpandingItems(expandingItems);
        resVO.setTopicList(topicItems);

        return AjaxResult.success(resVO);
    }

    private List<TopicItemDTO> getTopic(String keyword) {
        List<TopicItemDTO> topicItems = searchMapper.searTopic(keyword);
        if (topicItems.size() > 15) {
            Iterator<TopicItemDTO> iterator = topicItems.iterator();
            int index = 0;
            while (iterator.hasNext()) {
                TopicItemDTO itemDTO = iterator.next();
                if (index % 4 == 0 || itemDTO.getTitle() == null) {
                    iterator.remove();
                }
                index ++;
            }
        }
        return topicItems;
    }

    private List<ExpandingItem> getExpanding(String keyword) {

        List<ExpandingItem> expandingItems = searchMapper.searExpand(keyword);
        if (expandingItems.size() == 0) {
            return expandingItems;
        }
        List<ExpandingItem> titleList = new ArrayList<>();
        List<ExpandingItem> contentList = new ArrayList<>();
        List<ExpandingItem> bothList = new ArrayList<>();
        String regExp1="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。a-zA-Z0-9////]";
        String replace = "";
        for (ExpandingItem item : expandingItems) {
            if (item.getContent() == null) {
                titleList.add(item);
                continue;
            }
            String content = item.getContent();
            content = content.replaceAll(regExp1, replace);
            if (content.length() < 10) { continue;}
            item.setContent(content);

            if (!StringUtils.hasLength(content)) {
                if (item.getTitle().contains(keyword)) {
                    titleList.add(item);
                    continue;
                }
            }

            if (item.getTitle().contains(keyword) && item.getContent().contains(keyword)) {
                bothList.add(item);
                continue;
            }
            contentList.add(item);
        }

        expandingItems.clear();
        expandingItems.addAll(bothList);
        expandingItems.addAll(titleList);
        expandingItems.addAll(contentList);

        return expandingItems;
    }

    private List<MaterialItem> getMaterial(String keyword) {

        List<MaterialItem> materialItems = searchMapper.searMaterList(keyword);
        if (materialItems.size() == 0) {
            return materialItems;
        }
        List<MaterialItem> titleList = new ArrayList<>();
        List<MaterialItem> contentList = new ArrayList<>();
        List<MaterialItem> bothList = new ArrayList<>();
        String regExp1="[\n`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。 ？\"a-zA-Z\\\\0-9]";
        String replace = "";
        for (MaterialItem item : materialItems) {
            if (item.getContent() == null) {
                titleList.add(item);
                continue;
            }
            String content = item.getContent();
            content = content.replaceAll(regExp1, replace);
            if (content.length() < 10) { continue; }
            item.setContent(content);

            if (!StringUtils.hasLength(content)) {
                if (item.getTitle().contains(keyword)) {
                    titleList.add(item);
                    continue;
                }
            }

            if (item.getTitle().contains(keyword) && item.getContent().contains(keyword)) {
                bothList.add(item);
                continue;
            }
            contentList.add(item);
        }

        materialItems.clear();
        materialItems.addAll(bothList);
        materialItems.addAll(titleList);
        materialItems.addAll(contentList);

        return materialItems;

    }
}
