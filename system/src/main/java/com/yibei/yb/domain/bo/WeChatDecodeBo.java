package com.yibei.yb.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class WeChatDecodeBo {

//	@NotBlank(message = "encryptedData不能为空")
//	private String encryptedData;

	@NotBlank(message = "code不能为空")
	private String code;

	@ApiModelProperty("用户微信昵称")
	private String nickName;

	@ApiModelProperty("用户微信头像")
	private String avatar;

//	@NotBlank(message = "iv不能为空")
//	private String iv;
}
