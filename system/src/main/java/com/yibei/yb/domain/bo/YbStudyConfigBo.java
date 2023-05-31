package com.yibei.yb.domain.bo;

import com.yibei.common.core.domain.BaseEntity;
import com.yibei.common.core.domain.TreeEntity;
import com.yibei.common.core.validate.AddGroup;
import com.yibei.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;


/**
 * 教材学习配置业务对象 yb_study_config
 *
 * @author yibei
 * @date 2022-05-12
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("教材学习配置业务对象")
public class YbStudyConfigBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 教材id
     */
    @ApiModelProperty(value = "教材id", required = true)
    @NotNull(message = "教材id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long teachingMaterialId;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 每天学习数量
     */
    @ApiModelProperty(value = "每天学习数量", required = true)
    @NotNull(message = "每天学习数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long quantity;

    /**
     * 是否开启语音播放
     */
    @ApiModelProperty(value = "是否开启语音播放", required = true)
    @NotNull(message = "是否开启语音播放不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer isVoice;

    /**
     * 分页大小
     */
    @ApiModelProperty("分页大小")
    private Integer pageSize=20;

    /**
     * 当前页数
     */
    @ApiModelProperty("当前页数")
    private Integer pageNum=1;

    /**
     * 排序列
     */
    @ApiModelProperty("排序列")
    private String orderByColumn;

    /**
     * 排序的方向desc或者asc
     */
    @ApiModelProperty(value = "排序的方向", example = "asc,desc")
    private String isAsc;

}
