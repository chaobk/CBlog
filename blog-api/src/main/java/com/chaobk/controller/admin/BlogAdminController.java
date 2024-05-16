package com.chaobk.controller.admin;

import com.chaobk.annotation.OperationLogger;
import com.chaobk.entity.Category;
import com.chaobk.entity.Tag;
import com.chaobk.entity.User;
import com.chaobk.model.dto.Blog;
import com.chaobk.model.dto.BlogVisibility;
import com.chaobk.model.vo.Result;
import com.chaobk.service.BlogService;
import com.chaobk.service.CategoryService;
import com.chaobk.service.CommentService;
import com.chaobk.service.TagService;
import com.chaobk.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Description: 博客文章后台管理
 * @Author: Naccl
 * @Date: 2020-07-29
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@io.swagger.v3.oas.annotations.tags.Tag(name = "BlogAdminController - 博客文章后台管理")
public class BlogAdminController {
	private final BlogService blogService;
	private final CategoryService categoryService;
	private final TagService tagService;
	private final CommentService commentService;

	@GetMapping("/blogs")
	@Operation(description = "/admin/blogs - 获取博客文章列表")
	public Result blogs(@Parameter(description = "按标题模糊查询") @RequestParam(defaultValue = "") String title,
	                    @Parameter(description = "按分类id查询") @RequestParam(defaultValue = "") Integer categoryId,
	                    @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
						@Parameter(description = "每页个数") @RequestParam(defaultValue = "10") Integer pageSize) {
		String orderBy = "create_time desc";
		PageHelper.startPage(pageNum, pageSize, orderBy);
		PageInfo<com.chaobk.entity.Blog> pageInfo = new PageInfo<>(blogService.getListByTitleAndCategoryId(title, categoryId));
		List<Category> categories = categoryService.getCategoryList();
		Map<String, Object> map = new HashMap<>(4);
		map.put("blogs", pageInfo);
		map.put("categories", categories);
		return Result.ok("请求成功", map);
	}


	@OperationLogger("删除博客")
	@DeleteMapping("/blog")
	@Operation(description = "/admin/blog - 删除博客文章")
	public Result delete(@Parameter(description = "博客id") @RequestParam Long id) {
		blogService.deleteBlogTagByBlogId(id);
		blogService.deleteBlogById(id);
		commentService.deleteCommentsByBlogId(id);
		return Result.ok("删除成功");
	}

	@GetMapping("/categoryAndTag")
	@Operation(description = "/admin/categoryAndTag - 获取分类列表和标签列表")
	public Result categoryAndTag() {
		List<Category> categories = categoryService.getCategoryList();
		List<Tag> tags = tagService.getTagList();
		Map<String, Object> map = new HashMap<>(4);
		map.put("categories", categories);
		map.put("tags", tags);
		return Result.ok("请求成功", map);
	}

	@OperationLogger("更新博客置顶状态")
	@PutMapping("/blog/top")
	@Operation(description = "/admin/blog/top - 更新博客置顶状态")
	public Result updateTop(@RequestParam Long id, @RequestParam Boolean top) {
		blogService.updateBlogTopById(id, top);
		return Result.ok("操作成功");
	}

	@OperationLogger("更新博客推荐状态")
	@PutMapping("/blog/recommend")
	@Operation(description = "/admin/blog/recommend - 更新博客推荐状态")
	public Result updateRecommend(@RequestParam Long id, @RequestParam Boolean recommend) {
		blogService.updateBlogRecommendById(id, recommend);
		return Result.ok("操作成功");
	}

	@OperationLogger("更新博客可见性状态")
	@PutMapping("blog/{id}/visibility")
	@Operation(description = "/admin/blog/{id}/visibility - 更新博客可见性状态")
	public Result updateVisibility(@PathVariable Long id, @RequestBody BlogVisibility blogVisibility) {
		blogService.updateBlogVisibilityById(id, blogVisibility);
		return Result.ok("操作成功");
	}

	@GetMapping("/blog")
	@Operation(description = "/admin/blog - 获取博客文章详情")
	public Result getBlog(@Parameter(description = "博客id") @RequestParam Long id) {
		com.chaobk.entity.Blog blog = blogService.getBlogById(id);
		return Result.ok("获取成功", blog);
	}

	/**
	 * 保存草稿或发布新文章
	 *
	 * @param blog 博客文章DTO
	 * @return
	 */
	@OperationLogger("发布博客")
	@PostMapping("/blog")
	@Operation(description ="/admin/blog - 保存草稿或发布新文章")
	public Result saveBlog(@RequestBody Blog blog) {
		return getResult(blog, "save");
	}

	@OperationLogger("更新博客")
	@PutMapping("/blog")
	@Operation(description ="/admin/blog - 更新博客")
	public Result updateBlog(@RequestBody Blog blog) {
		return getResult(blog, "update");
	}

	/**
	 * 执行博客添加或更新操作：校验参数是否合法，添加分类、标签，维护博客标签关联表
	 *
	 * @param blog 博客文章DTO
	 * @param type 添加或更新
	 * @return
	 */
	private Result getResult(Blog blog, String type) {
		//验证普通字段
		if (StringUtils.isEmpty(blog.getTitle(), blog.getFirstPicture(), blog.getContent(), blog.getDescription())
				|| blog.getWords() == null || blog.getWords() < 0) {
			return Result.error("参数有误");
		}

		//处理分类
		Object cate = blog.getCate();
		if (cate == null) {
			return Result.error("分类不能为空");
		}
		if (cate instanceof Integer) {//选择了已存在的分类
			Category c = categoryService.getCategoryById(((Integer) cate).longValue());
			blog.setCategory(c);
		} else if (cate instanceof String) {//添加新分类
			//查询分类是否已存在
			Category category = categoryService.getCategoryByName((String) cate);
			if (category != null) {
				return Result.error("不可添加已存在的分类");
			}
			Category c = new Category();
			c.setName((String) cate);
			categoryService.saveCategory(c);
			blog.setCategory(c);
		} else {
			return Result.error("分类不正确");
		}

		//处理标签
		List<Object> tagList = blog.getTagList();
		List<Tag> tags = new ArrayList<>();
		for (Object t : tagList) {
			if (t instanceof Integer) {//选择了已存在的标签
				Tag tag = tagService.getTagById(((Integer) t).longValue());
				tags.add(tag);
			} else if (t instanceof String) {//添加新标签
				//查询标签是否已存在
				Tag tag1 = tagService.getTagByName((String) t);
				if (tag1 != null) {
					return Result.error("不可添加已存在的标签");
				}
				Tag tag = new Tag();
				tag.setName((String) t);
				tagService.saveTag(tag);
				tags.add(tag);
			} else {
				return Result.error("标签不正确");
			}
		}

		Date date = new Date();
		if (blog.getReadTime() == null || blog.getReadTime() < 0) {
			blog.setReadTime((int) Math.round(blog.getWords() / 200.0));//粗略计算阅读时长
		}
		if (blog.getViews() == null || blog.getViews() < 0) {
			blog.setViews(0);
		}
		if ("save".equals(type)) {
			blog.setCreateTime(date);
			blog.setUpdateTime(date);
			User user = new User();
			user.setId(1L);//个人博客默认只有一个作者
			blog.setUser(user);

			blogService.saveBlog(blog);
			//关联博客和标签(维护 blog_tag 表)
			for (Tag t : tags) {
				blogService.saveBlogTag(blog.getId(), t.getId());
			}
			return Result.ok("添加成功");
		} else {
			blog.setUpdateTime(date);
			blogService.updateBlog(blog);
			//关联博客和标签(维护 blog_tag 表)
			blogService.deleteBlogTagByBlogId(blog.getId());
			for (Tag t : tags) {
				blogService.saveBlogTag(blog.getId(), t.getId());
			}
			return Result.ok("更新成功");
		}
	}
}
