package com.chaobk.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: 登录账号密码
 * @Author: Naccl
 * @Date: 2020-09-02
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel("登录账号密码")
public class LoginInfo {
	@ApiModelProperty(value = "账号")
	private String username;
	@ApiModelProperty(value = "密码")
	private String password;
}
