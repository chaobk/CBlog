package com.chaobk.controller;

import com.chaobk.entity.Category;
import com.chaobk.entity.Tag;
import com.chaobk.model.vo.NewBlog;
import com.chaobk.model.vo.RandomBlog;
import com.chaobk.model.vo.Result;
import com.chaobk.service.BlogService;
import com.chaobk.service.CategoryService;
import com.chaobk.service.SiteSettingService;
import com.chaobk.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Description: 站点相关
 * @Author: Naccl
 * @Date: 2020-08-09
 */

@RestController
@RequiredArgsConstructor
public class IndexController {
	private final SiteSettingService siteSettingService;
	private final BlogService blogService;
	private final CategoryService categoryService;
	private final TagService tagService;

	/**
	 * 获取站点配置信息、最新推荐博客、分类列表、标签云、随机博客
	 *
	 * @return
	 */
	@GetMapping("/site")
	public Result site() {
		Map<String, Object> map = siteSettingService.getSiteInfo();
		List<NewBlog> newBlogList = blogService.getNewBlogListByIsPublished();
		List<Category> categoryList = categoryService.getCategoryNameList();
		List<Tag> tagList = tagService.getTagListNotId();
		List<RandomBlog> randomBlogList = blogService.getRandomBlogListByLimitNumAndIsPublishedAndIsRecommend();
		map.put("newBlogList", newBlogList);
		map.put("categoryList", categoryList);
		map.put("tagList", tagList);
		map.put("randomBlogList", randomBlogList);
		return Result.ok("请求成功", map);
	}
}
