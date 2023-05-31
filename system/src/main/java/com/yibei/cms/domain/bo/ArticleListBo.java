package com.yibei.cms.domain.bo;


import com.yibei.system.domain.clientBo.PageBo;
import lombok.Data;

@Data
public class ArticleListBo extends PageBo {
    private String code;
    private String categoryCode;
    private Long categoryId;
    private String keyword;
    private Integer isTop;
    private Integer isRecommend;
}
