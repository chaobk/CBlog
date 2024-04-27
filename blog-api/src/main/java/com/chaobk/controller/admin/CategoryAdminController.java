package com.chaobk.controller.admin;

import com.chaobk.annotation.OperationLogger;
import com.chaobk.entity.Category;
import com.chaobk.model.vo.Result;
import com.chaobk.service.BlogService;
import com.chaobk.service.CategoryService;
import com.chaobk.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 博客分类后台管理
 * @Author: Naccl
 * @Date: 2020-08-02
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Api(tags = "CategoryAdminController - 博客分类后台管理")
public class CategoryAdminController {
	private final BlogService blogService;
	private final CategoryService categoryService;

	@GetMapping("/categories")
	@ApiOperation("/admin/categories - 获取博客分类列表")
	public Result categories(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize) {
		String orderBy = "id desc";
		PageHelper.startPage(pageNum, pageSize, orderBy);
		PageInfo<Category> pageInfo = new PageInfo<>(categoryService.getCategoryList());
		return Result.ok("请求成功", pageInfo);
	}

	@OperationLogger("添加分类")
	@PostMapping("/category")
	@ApiOperation("添加新分类")
	public Result saveCategory(@RequestBody Category category) {
		return getResult(category, "save");
	}

	@OperationLogger("修改分类")
	@PutMapping("/category")
	public Result updateCategory(@RequestBody Category category) {
		return getResult(category, "update");
	}

	/**
	 * 执行分类添加或更新操作：校验参数是否合法，分类是否已存在
	 *
	 * @param category 分类实体
	 * @param type     添加或更新
	 * @return
	 */
	private Result getResult(Category category, String type) {
		if (StringUtils.isEmpty(category.getName())) {
			return Result.error("分类名称不能为空");
		}
		//查询分类是否已存在
		Category category1 = categoryService.getCategoryByName(category.getName());
		//如果 category1.getId().equals(category.getId()) == true 就是更新分类
		if (category1 != null && !category1.getId().equals(category.getId())) {
			return Result.error("该分类已存在");
		}
		if ("save".equals(type)) {
			categoryService.saveCategory(category);
			return Result.ok("分类添加成功");
		} else {
			categoryService.updateCategory(category);
			return Result.ok("分类更新成功");
		}
	}

	@OperationLogger("删除分类")
	@DeleteMapping("/category")
	@ApiOperation("删除分类")
	public Result delete(@ApiParam("分类的id") @RequestParam Long id) {
		//删除存在博客关联的分类后，该博客的查询会出异常
		int num = blogService.countBlogByCategoryId(id);
		if (num != 0) {
			return Result.error("已有博客与此分类关联，不可删除");
		}
		categoryService.deleteCategoryById(id);
		return Result.ok("删除成功");
	}
}
