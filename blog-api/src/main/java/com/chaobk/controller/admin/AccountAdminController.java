package com.chaobk.controller.admin;

import com.chaobk.entity.User;
import com.chaobk.model.vo.Result;
import com.chaobk.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 账号后台管理
 * @Author: Naccl
 * @Date: 2023-01-31
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Api(tags = "AccountAdminController - 账号后台管理")
public class AccountAdminController {
	private final UserService userService;

	/**
	 * 账号密码修改
	 */
	@PostMapping("/account")
	@ApiOperation("账号密码修改")
	public Result account(@RequestBody User user, @RequestHeader(value = "Authorization", defaultValue = "") String jwt) {
		boolean res = userService.changeAccount(user, jwt);
		return res ? Result.ok("修改成功") : Result.error("修改失败");
	}
}
