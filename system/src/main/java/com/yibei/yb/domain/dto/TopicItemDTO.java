package com.yibei.yb.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

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
 * 　 @createDate 2023年06月01日 20:29
 * 　 @since JDK1.8
 */
@Getter
@Setter
public class TopicItemDTO {

    @ApiModelProperty("题库的id")
    private Long questionBankId;

    @ApiModelProperty("题库的名字")
    private String questionBankName;

    @ApiModelProperty("题目标题")
    private String title;

    @ApiModelProperty("题库标签")
    private String label;
}
