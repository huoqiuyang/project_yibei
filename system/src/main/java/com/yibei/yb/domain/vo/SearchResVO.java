package com.yibei.yb.domain.vo;

import com.yibei.yb.domain.dto.ExpandingItem;
import com.yibei.yb.domain.dto.MaterialItem;
import com.yibei.yb.domain.dto.TopicItemDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
 * 　 @createDate 2023年06月01日 20:22
 * 　 @since JDK1.8
 */
@Getter
@Setter
public class SearchResVO {

    @ApiModelProperty("教材结果列表")
    private List<MaterialItem> materialList;

    @ApiModelProperty("题库结果列表")
    private List<TopicItemDTO> topicList;

    @ApiModelProperty("拓展阅读结果列表")
    private List<ExpandingItem> expandingItems;

}
