#词条添加字段-拓展信息
ALTER TABLE yb_teaching_material_entry ADD column extending_info longtext DEFAULT NULL COMMENT '拓展信息' AFTER related_links;
#词条添加字段-答题点播
ALTER TABLE yb_teaching_material_entry ADD column answer_tip longtext DEFAULT NULL COMMENT '答题点播' AFTER extending_info;