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


@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
@Tag(name = "CategoryController - 分类")
public class CategoryController {
	private final BlogService blogService;


	@VisitLogger(VisitBehavior.CATEGORY)
	@GetMapping
	@Operation(description ="根据分类name分页查询公开博客列表")
	public Result category(@Parameter(description = "分类名") @RequestParam String categoryName,
                           @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum) {
		PageResult<BlogInfo> pageResult = blogService.getBlogInfoListByCategoryNameAndIsPublished(categoryName, pageNum);
		return Result.ok("请求成功", pageResult);
	}
}
