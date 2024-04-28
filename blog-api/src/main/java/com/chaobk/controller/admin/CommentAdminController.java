package com.chaobk.controller.admin;

import com.chaobk.annotation.OperationLogger;
import com.chaobk.entity.Blog;
import com.chaobk.entity.Comment;
import com.chaobk.model.vo.Result;
import com.chaobk.service.BlogService;
import com.chaobk.service.CommentService;
import com.chaobk.util.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 博客评论后台管理
 * @Author: Naccl
 * @Date: 2020-08-03
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Api(tags = "CommentAdminController - 博客评论后台管理")
public class CommentAdminController {
	private final CommentService commentService;
	private final BlogService blogService;

	/**
	 * 按页面和博客id分页查询评论List
	 *
	 * @param page     要查询的页面(博客文章or关于我...)
	 * @param blogId   如果是博客文章页面 需要提供博客id
	 * @param pageNum  页码
	 * @param pageSize 每页个数
	 * @return
	 */
	@GetMapping("/comments")
	@ApiOperation("按页面和博客id分页查询评论List")
	public Result comments(@RequestParam(defaultValue = "") Integer page,
	                       @RequestParam(defaultValue = "") Long blogId,
	                       @RequestParam(defaultValue = "1") Integer pageNum,
	                       @RequestParam(defaultValue = "10") Integer pageSize) {
		String orderBy = "create_time desc";
		PageHelper.startPage(pageNum, pageSize, orderBy);
		List<Comment> comments = commentService.getListByPageAndParentCommentId(page, blogId, -1L);
		PageInfo<Comment> pageInfo = new PageInfo<>(comments);
		return Result.ok("请求成功", pageInfo);
	}

	/**
	 * 获取所有博客id和title 供评论分类的选择
	 *
	 * @return
	 */
	@GetMapping("/blogIdAndTitle")
	@ApiOperation("获取所有博客id和title 供评论分类的选择")
	public Result blogIdAndTitle() {
		List<Blog> blogs = blogService.getIdAndTitleList();
		return Result.ok("请求成功", blogs);
	}

	/**
	 * 更新评论公开状态
	 *
	 * @param id        评论id
	 * @param published 是否公开
	 * @return
	 */
	@OperationLogger("更新评论公开状态")
	@PutMapping("/comment/published")
	@ApiOperation("更新评论公开状态")
	public Result updatePublished(@ApiParam("评论id") @RequestParam Long id, @ApiParam("是否公开") @RequestParam Boolean published) {
		commentService.updateCommentPublishedById(id, published);
		return Result.ok("操作成功");
	}

	/**
	 * 更新评论接收邮件提醒状态
	 *
	 * @param id     评论id
	 * @param notice 是否接收提醒
	 * @return
	 */
	@OperationLogger("更新评论邮件提醒状态")
	@PutMapping("/comment/notice")
	@ApiOperation("更新评论邮件提醒状态")
	public Result updateNotice(@ApiParam("评论id") @RequestParam Long id, @ApiParam("是否接收提醒") @RequestParam Boolean notice) {
		commentService.updateCommentNoticeById(id, notice);
		return Result.ok("操作成功");
	}

	@OperationLogger("删除评论")
	@DeleteMapping("/comment")
	@ApiOperation("按id删除该评论及其所有子评论")
	public Result delete(@ApiParam("评论id") @RequestParam Long id) {
		commentService.deleteCommentById(id);
		return Result.ok("删除成功");
	}

	@OperationLogger("修改评论")
	@PutMapping("/comment")
	@ApiOperation("修改评论")
	public Result updateComment(@RequestBody Comment comment) {
		if (StringUtils.isEmpty(comment.getNickname(), comment.getAvatar(), comment.getEmail(), comment.getIp(), comment.getContent())) {
			return Result.error("参数有误");
		}
		commentService.updateComment(comment);
		return Result.ok("评论修改成功");
	}
}
