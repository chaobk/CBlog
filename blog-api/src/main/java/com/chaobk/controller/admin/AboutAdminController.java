package com.chaobk.controller.admin;

import com.chaobk.annotation.OperationLogger;
import com.chaobk.model.vo.Result;
import com.chaobk.service.AboutService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description: 关于我页面后台管理
 * @Author: Naccl
 * @Date: 2020-09-01
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "AboutAdminController - 关于我页面 后台管理")
public class AboutAdminController {
	private final AboutService aboutService;

	/**
	 * 获取关于我页面配置
	 *
	 * @return
	 */
	@GetMapping("/about")
	@Operation(description = "获取关于我页面配置")
	public Result about() {
		return Result.ok("请求成功", aboutService.getAboutSetting());
	}

	/**
	 * 修改关于我页面
	 *
	 * @param map
	 * @return
	 */
	@OperationLogger("修改关于我页面")
	@PutMapping("/about")
	@Operation(description ="修改关于我页面")
	public Result updateAbout(@RequestBody Map<String, String> map) {
		aboutService.updateAbout(map);
		return Result.ok("修改成功");
	}
}
