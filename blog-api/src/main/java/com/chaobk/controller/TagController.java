package com.chaobk.controller;

import com.chaobk.annotation.VisitLogger;
import com.chaobk.enums.VisitBehavior;
import com.chaobk.model.vo.BlogInfo;
import com.chaobk.model.vo.PageResult;
import com.chaobk.model.vo.Result;
import com.chaobk.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 标签
 * @Author: Naccl
 * @Date: 2020-08-17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
@Tag(name = "TagController - 标签")
public class TagController {
	private final BlogService blogService;

	/**
	 * 根据标签name分页查询公开博客列表
	 *
	 * @param tagName 标签name
	 * @param pageNum 页码
	 * @return
	 */
	@VisitLogger(VisitBehavior.TAG)
	@GetMapping
	@Operation(description ="根据标签name分页查询公开博客列表")
	public Result tag(@Parameter(description = "标签名") @RequestParam String tagName,
                      @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum) {
		PageResult<BlogInfo> pageResult = blogService.getBlogInfoListByTagNameAndIsPublished(tagName, pageNum);
		return Result.ok("请求成功", pageResult);
	}
}
