package com.yibei.system.domain.bo;

import com.yibei.common.core.domain.BaseEntity;
import com.yibei.common.core.validate.AddGroup;
import com.yibei.common.core.validate.EditGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.*;

import java.util.Date;

/**
 * 验证码记录业务对象 verification_code
 *
 * @author yibei
 * @date 2021-10-24
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel("验证码记录业务对象")
public class VerificationCodeBo extends BaseEntity {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID", required = true)
    @NotNull(message = "ID不能为空", groups = { AddGroup.class })
    private Long id;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号", required = true)
    @NotBlank(message = "账号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String account;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String code;

    /**
     * 验证码类型
     */
    @ApiModelProperty(value = "验证码类型", required = true)
    @NotNull(message = "验证码类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long type;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态", required = true)
    @NotNull(message = "状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 失效时间
     */
    @ApiModelProperty(value = "失效时间", required = true)
    @NotNull(message = "失效时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date invalidTime;

    /**
     * 删除标志
     */
    @ApiModelProperty(value = "删除标志", required = true)
    @NotNull(message = "删除标志不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer isDeleted;


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
