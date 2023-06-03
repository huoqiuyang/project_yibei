package com.yibei.client.controller.yb;

import com.yibei.common.core.domain.AjaxResult;
import com.yibei.framework.sso.LoginChecked;
import com.yibei.yb.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * 　 @createDate 2023年06月01日 20:14
 * 　 @since JDK1.8
 */
@RestController("Client_SearchKeywordController")
@Api(value = "搜索模块",tags = "搜索模块")
@RequestMapping("/client/searchModel")
public class SearchKeywordController {


    @Resource
    private SearchService searchService;

    /**
     * 搜索关键字
     * @author huoqy
     * @date 2023/6/1
     * @param  * @param keyword
     * @return com.yibei.common.core.domain.AjaxResult
     */
    @GetMapping("/keywordSearch")
    @ApiOperation("内容搜索")
    @LoginChecked
    public AjaxResult search(String keyword){
        return searchService.keywordSearch(keyword);
    }
}
