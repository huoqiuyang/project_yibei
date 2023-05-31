package com.yibei.cms.domain;

import lombok.Data;

import java.util.List;

@Data
public class CmsCategoryTreeNode {
    private Long id;
    private String name;
    private Long createChildEnable;
    private Long childTemplateId;
    private Long parentId;
    private List<CmsCategoryTreeNode> children;
}
