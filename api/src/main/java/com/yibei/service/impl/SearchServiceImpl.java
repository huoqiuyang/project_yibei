package com.yibei.service.impl;

import com.yibei.common.core.domain.AjaxResult;
import com.yibei.mapper.SearchMapper;
import com.yibei.service.SearchService;
import com.yibei.yb.domain.vo.SearchResVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

        resVO.setMaterialList(searchMapper.searMaterList(keyword));
        resVO.setExpandingItems(searchMapper.searExpand(keyword));
        resVO.setTopicList(searchMapper.searTopic(keyword));

        return null;
    }
}
