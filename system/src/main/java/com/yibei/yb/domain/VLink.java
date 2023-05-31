package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 相关链接对象 v_link
 *
 * @author yibei
 * @date 2022-06-09
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("v_link")
public class VLink implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * 类型
     */
    @TableField(value = "`type`")
    private String type;

    /**
     * 词条ID
     */
    @TableField(value = "`id`")
    private Long id;

    /**
     * 书籍ID
     */
    @TableField(value = "`book_id`")
    private Long bookId;

    /**
     * 标题
     */
    @TableField(value = "`title`")
    private String title;

    /**
     * 标签
     */
    @TableField(value = "`label`")
    private String label;

    /**
     * 图片
     */
    @TableField(value = "`img_url`")
    private String imgUrl;

    /**
     * 重要度
     */
    @TableField(value = "`importance`")
    private String importance;

    /**
     * 排序
     */
    @TableField(value = "`sort`")
    private Long sort;

    /**
     * 书籍名称
     */
    @TableField(value = "`book_title`")
    private String bookTitle;
}
