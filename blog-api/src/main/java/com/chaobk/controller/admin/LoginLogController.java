package com.chaobk.controller.admin;

import com.chaobk.entity.LoginLog;
import com.chaobk.model.vo.Result;
import com.chaobk.service.LoginLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 登录日志后台管理
 * @Author: Naccl
 * @Date: 2020-12-03
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "LoginLogController - 登录日志后台管理")
public class LoginLogController {
	private final LoginLogService loginLogService;

	/**
	 * 分页查询登录日志列表
	 *
	 * @param date     按操作时间查询
	 * @param pageNum  页码
	 * @param pageSize 每页个数
	 * @return
	 */
	@GetMapping("/loginLogs")
	@Operation(description ="分页查询登录日志列表")
	public Result loginLogs(@RequestParam(defaultValue = "") String[] date,
	                        @RequestParam(defaultValue = "1") Integer pageNum,
	                        @RequestParam(defaultValue = "10") Integer pageSize) {
		String startDate = null;
		String endDate = null;
		if (date.length == 2) {
			startDate = date[0];
			endDate = date[1];
		}
		String orderBy = "create_time desc";
		PageHelper.startPage(pageNum, pageSize, orderBy);
		PageInfo<LoginLog> pageInfo = new PageInfo<>(loginLogService.getLoginLogListByDate(startDate, endDate));
		return Result.ok("请求成功", pageInfo);
	}

	/**
	 * 按id删除登录日志
	 *
	 * @param id 日志id
	 * @return
	 */
	@DeleteMapping("/loginLog")
	@Operation(description ="按id删除登录日志")
	public Result delete(@RequestParam Long id) {
		loginLogService.deleteLoginLogById(id);
		return Result.ok("删除成功");
	}
}
