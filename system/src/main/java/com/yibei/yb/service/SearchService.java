package com.yibei.yb.service;

import com.yibei.common.core.domain.AjaxResult;

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
public interface SearchService {
    /**
     * 关键字搜索
     * @author huoqy
     * @date 2023/6/1
     * @param  * @param keyword
     * @return com.yibei.common.core.domain.AjaxResult
     */
    AjaxResult keywordSearch(String keyword);
}
