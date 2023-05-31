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
 * 学习时间记录业务对象 yb_study_time_log
 *
 * @author yibei
 * @date 2022-05-18
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("学习时间记录业务对象")
public class YbStudyTimeLogBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", required = true)
    @NotNull(message = "用户id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型", required = true)
    @NotNull(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer type;

    /**
     * 内容id
     */
    @ApiModelProperty(value = "内容id", required = true)
    @NotNull(message = "内容id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long contentId;

    /**
     * 累计分钟数
     */
    @ApiModelProperty(value = "累计分钟数", required = true)
    @NotNull(message = "累计分钟数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long min;

    /**
     * 日期
     */
    @ApiModelProperty(value = "日期", required = true)
    @NotBlank(message = "日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dateDay;


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
