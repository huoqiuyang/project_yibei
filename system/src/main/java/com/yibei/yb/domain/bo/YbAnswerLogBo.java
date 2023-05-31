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
import java.util.Date;


/**
 * 答题记录业务对象 yb_answer_log
 *
 * @author yibei
 * @date 2022-05-07
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("答题记录业务对象")
public class YbAnswerLogBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 题目id
     */
    @ApiModelProperty(value = "题目id", required = true)
    @NotNull(message = "题目id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long questionBankContentId;

    /**
     * 题库id
     */
    @ApiModelProperty(value = "题库id", required = true)
    @NotNull(message = "题库id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long questionBankId;

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

    @ApiModelProperty("修改时间")
    private Date lastTime;

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
