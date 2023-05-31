package com.yibei.yb.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
 * 教材学习配置对象 yb_study_config
 *
 * @author yibei
 * @date 2022-05-12
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("yb_study_config")
public class YbStudyConfig implements Serializable {

    private static final long serialVersionUID=1L;


    /**
     * ID
     */
    @TableId(value = "id")
    @TableField(value = "`id`")
    private Long id;

    /**
     * 教材id
     */
    @TableField(value = "`teaching_material_id`")
    private Long teachingMaterialId;

    /**
     * 用户id
     */
    @TableField(value = "`user_id`")
    private Long userId;

    /**
     * 每天学习数量
     */
    @TableField(value = "`quantity`")
    private Long quantity;

    /**
     * 是否开启语音播放
     */
    @TableField(value = "`is_voice`")
    private Integer isVoice;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 删除 0否 1是
     */
    @TableField(value = "`is_deleted`")
    private Integer isDeleted;
}
