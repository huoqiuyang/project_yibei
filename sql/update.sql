-- 词条添加字段-拓展信息
ALTER TABLE yb_teaching_material_entry ADD column extending_info longtext DEFAULT NULL COMMENT '拓展信息' AFTER related_links;
-- 词条添加字段-答题点播
ALTER TABLE yb_teaching_material_entry ADD column answer_tip longtext DEFAULT NULL COMMENT '答题点播' AFTER extending_info;

-- 新增听书进度表
CREATE TABLE `yb_listening_book_speed`  (
                                         `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                         `user_id` bigint(20) NOT NULL COMMENT '用户id',
                                         `material_id` bigint(20) NOT NULL COMMENT '教材id',
                                         `entry_id` bigint(20) NOT NULL COMMENT '上次听的词条的id',
                                         `entry_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '上次听的词条的名字',
                                         `proportion` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '听书进度百分比',
                                         `last_location` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '上次听的词条的位置',
                                         PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户听书进度表' ROW_FORMAT = Dynamic;