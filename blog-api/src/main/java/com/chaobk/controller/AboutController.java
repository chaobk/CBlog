package com.chaobk.controller;

import com.chaobk.annotation.VisitLogger;
import com.chaobk.enums.VisitBehavior;
import com.chaobk.model.vo.Result;
import com.chaobk.service.AboutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 关于我页面
 * @Author: Naccl
 * @Date: 2020-08-31
 */
@RestController
@Tag(name = "AboutController - 关于我页面")
@RequiredArgsConstructor
public class AboutController {
	private final AboutService aboutService;

	/**
	 * 获取关于我页面信息
	 *
	 * @return
	 */
	@Operation(description ="获取关于我页面信息")
	@VisitLogger(VisitBehavior.ABOUT)
	@GetMapping("/about")
	public Result about() {
		return Result.ok("获取成功", aboutService.getAboutInfo());
	}
}
