package com.chaobk.controller.admin;

import com.chaobk.annotation.OperationLogger;
import com.chaobk.model.dto.Friend;
import com.chaobk.model.vo.Result;
import com.chaobk.service.FriendService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Description: 友链页面后台管理
 * @Author: Naccl
 * @Date: 2020-09-08
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "FriendAdminController - 友链页面后台管理")
public class FriendAdminController {
	private final FriendService friendService;

	/**
	 * 分页获取友链列表
	 *
	 * @param pageNum  页码
	 * @param pageSize 每页条数
	 * @return
	 */
	@GetMapping("/friends")
	@Operation(description ="分页获取友联列表")
	public Result friends(@RequestParam(defaultValue = "1") Integer pageNum,
	                      @RequestParam(defaultValue = "10") Integer pageSize) {
		String orderBy = "create_time asc";
		PageHelper.startPage(pageNum, pageSize, orderBy);
		PageInfo<com.chaobk.entity.Friend> pageInfo = new PageInfo<>(friendService.getFriendList());
		return Result.ok("请求成功", pageInfo);
	}

	/**
	 * 更新友链公开状态
	 *
	 * @param id        友链id
	 * @param published 是否公开
	 * @return
	 */
	@OperationLogger("更新友链公开状态")
	@PutMapping("/friend/published")
	@Operation(description ="更新友链公开状态")
	public Result updatePublished(@RequestParam Long id, @RequestParam Boolean published) {
		friendService.updateFriendPublishedById(id, published);
		return Result.ok("操作成功");
	}

	/**
	 * 添加友链
	 *
	 * @param friend 友链DTO
	 * @return
	 */
	@OperationLogger("添加友链")
	@PostMapping("/friend")
	@Operation(description ="添加友链")
	public Result saveFriend(@RequestBody com.chaobk.entity.Friend friend) {
		friendService.saveFriend(friend);
		return Result.ok("添加成功");
	}

	/**
	 * 更新友链
	 *
	 * @param friend 友链DTO
	 * @return
	 */
	@OperationLogger("更新友链")
	@PutMapping("/friend")
	public Result updateFriend(@RequestBody Friend friend) {
		friendService.updateFriend(friend);
		return Result.ok("修改成功");
	}

	/**
	 * 按id删除友链
	 *
	 * @param id
	 * @return
	 */
	@OperationLogger("删除友链")
	@DeleteMapping("/friend")
	@Operation(description ="按id删除友链")
	public Result deleteFriend(@RequestParam Long id) {
		friendService.deleteFriend(id);
		return Result.ok("删除成功");
	}

	/**
	 * 获取友链页面信息
	 *
	 * @return
	 */
	@GetMapping("/friendInfo")
	@Operation(description ="获取友链页面信息")
	public Result friendInfo() {
		return Result.ok("请求成功", friendService.getFriendInfo(false, false));
	}

	/**
	 * 修改友链页面评论开放状态
	 *
	 * @param commentEnabled 是否开放评论
	 * @return
	 */
	@OperationLogger("修改友链页面评论开放状态")
	@PutMapping("/friendInfo/commentEnabled")
	@Operation(description ="修改友链页面评论开放状态")
	public Result updateFriendInfoCommentEnabled(@RequestParam Boolean commentEnabled) {
		friendService.updateFriendInfoCommentEnabled(commentEnabled);
		return Result.ok("修改成功");
	}

	/**
	 * 修改友链页面content
	 *
	 * @param map 包含content的JSON对象
	 * @return
	 */
	@OperationLogger("修改友链页面信息")
	@PutMapping("/friendInfo/content")
	@Operation(description ="修改友链页面信息")
	public Result updateFriendInfoContent(@RequestBody Map map) {
		friendService.updateFriendInfoContent((String) map.get("content"));
		return Result.ok("修改成功");
	}
}
