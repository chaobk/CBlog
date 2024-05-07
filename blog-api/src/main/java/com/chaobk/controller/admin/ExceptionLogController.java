package com.chaobk.controller.admin;

import com.chaobk.entity.ExceptionLog;
import com.chaobk.model.vo.Result;
import com.chaobk.service.ExceptionLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 异常日志后台管理
 * @Author: Naccl
 * @Date: 2020-12-04
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Api(tags = "exceptionLogController - 异常日志后台管理")
public class ExceptionLogController {
	private final ExceptionLogService exceptionLogService;

	/**
	 * 分页查询异常日志列表
	 *
	 * @param date     按操作时间查询
	 * @param pageNum  页码
	 * @param pageSize 每页个数
	 * @return
	 */
	@GetMapping("/exceptionLogs")
	@ApiOperation("分页查询异常日志列表")
	public Result exceptionLogs(@ApiParam("按操作时间查询") @RequestParam(defaultValue = "") String[] date,
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
		PageInfo<ExceptionLog> pageInfo = new PageInfo<>(exceptionLogService.getExceptionLogListByDate(startDate, endDate));
		return Result.ok("请求成功", pageInfo);
	}

	@DeleteMapping("/exceptionLog")
	@ApiOperation("按id删除异常日志")
	public Result delete(@ApiParam("要删除的日志id") @RequestParam Long id) {
		exceptionLogService.deleteExceptionLogById(id);
		return Result.ok("删除成功");
	}
}
