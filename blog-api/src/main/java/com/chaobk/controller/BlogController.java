package com.chaobk.controller;

import com.chaobk.annotation.VisitLogger;
import com.chaobk.constant.JwtConstants;
import com.chaobk.entity.User;
import com.chaobk.enums.VisitBehavior;
import com.chaobk.model.dto.BlogPassword;
import com.chaobk.model.vo.*;
import com.chaobk.service.BlogService;
import com.chaobk.service.impl.UserServiceImpl;
import com.chaobk.util.JwtUtils;
import com.chaobk.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 博客相关
 * @Author: Naccl
 * @Date: 2020-08-12
 */
@RestController
@Tag(name = "BlogController - 博客相关")
@RequiredArgsConstructor
public class BlogController {
	private final BlogService blogService;
    private final UserServiceImpl userService;

	/**
	 * 按置顶、创建时间排序 分页查询博客简要信息列表
	 *
	 * @param pageNum 页码
	 * @return
	 */
	@VisitLogger(VisitBehavior.INDEX)
	@GetMapping("/blogs")
	@Operation(description ="分页查询博客摘要信息列表， 按照置顶、创建时间排序")
	public Result blogs(@RequestParam(defaultValue = "1") Integer pageNum) {
		PageResult<BlogInfo> pageResult = blogService.getBlogInfoListByIsPublished(pageNum);
		return Result.ok("请求成功", pageResult);
	}

	@VisitLogger(VisitBehavior.BLOG)
	@GetMapping("/blog")
	@Operation(description ="按id获取公开博客详情")
	public Result getBlog(@Parameter(description = "博客id") @RequestParam Long id,
	                      @Parameter(description = "保护文章的访问Token") @RequestHeader(value = "Authorization", defaultValue = "") String jwt) {
		BlogDetail blog = blogService.getBlogByIdAndIsPublished(id);
		//对密码保护的文章校验Token
		if (!"".equals(blog.getPassword())) {
			if (JwtUtils.judgeTokenIsExist(jwt)) {
				try {
					String subject = JwtUtils.getTokenBody(jwt).getSubject();
					if (subject.startsWith(JwtConstants.ADMIN_PREFIX)) {
						//博主身份Token
						String username = subject.replace(JwtConstants.ADMIN_PREFIX, "");
						User admin = (User) userService.loadUserByUsername(username);
						if (admin == null) {
							return Result.create(403, "博主身份Token已失效，请重新登录！");
						}
					} else {
						//经密码验证后的Token
						Long tokenBlogId = Long.parseLong(subject);
						//博客id不匹配，验证不通过，可能博客id改变或客户端传递了其它密码保护文章的Token
						if (!tokenBlogId.equals(id)) {
							return Result.create(403, "Token不匹配，请重新验证密码！");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return Result.create(403, "Token已失效，请重新验证密码！");
				}
			} else {
				return Result.create(403, "此文章受密码保护，请验证密码！");
			}
			blog.setPassword("");
		}
		blogService.updateViewsToRedis(id);
		return Result.ok("获取成功", blog);
	}


	@VisitLogger(VisitBehavior.CHECK_PASSWORD)
	@PostMapping("/checkBlogPassword")
	@Operation(description ="校验受保护文章密码是否正确，正确则返回jwt")
	public Result checkBlogPassword(@RequestBody BlogPassword blogPassword) {
		String password = blogService.getBlogPassword(blogPassword.getBlogId());
		if (password.equals(blogPassword.getPassword())) {
			//生成有效时间一个月的Token
			String jwt = JwtUtils.generateToken(blogPassword.getBlogId().toString(), 1000 * 3600 * 24 * 30L);
			return Result.ok("密码正确", jwt);
		} else {
			return Result.create(403, "密码错误");
		}
	}


	@VisitLogger(VisitBehavior.SEARCH)
	@GetMapping("/searchBlog")
	@Operation(description ="按关键字搜索公开且无密码保护的博客文章")
	public Result searchBlog(@Parameter(description = "搜索关键字") @RequestParam String query) {
		//校验关键字字符串合法性
		if (StringUtils.isEmpty(query) || StringUtils.hasSpecialChar(query) || query.trim().length() > 20) {
			return Result.error("参数错误");
		}
		List<SearchBlog> searchBlogs = blogService.getSearchBlogListByQueryAndIsPublished(query.trim());
		return Result.ok("获取成功", searchBlogs);
	}
}
