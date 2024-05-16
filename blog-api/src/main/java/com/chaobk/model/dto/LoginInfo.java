package com.chaobk.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema
public class LoginInfo {
	@Schema(description =  "账号")
	private String username;
	@Schema(description =  "密码")
	private String password;
}
