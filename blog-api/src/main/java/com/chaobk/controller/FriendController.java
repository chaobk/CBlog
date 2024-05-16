package com.chaobk.controller;

import com.chaobk.annotation.VisitLogger;
import com.chaobk.enums.VisitBehavior;
import com.chaobk.model.vo.Friend;
import com.chaobk.model.vo.FriendInfo;
import com.chaobk.model.vo.Result;
import com.chaobk.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 友链
 * @Author: Naccl
 * @Date: 2020-09-08
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "FriendController - 友链")
public class FriendController {
	private final FriendService friendService;


	@VisitLogger(VisitBehavior.FRIEND)
	@GetMapping("/friends")
	@Operation(description ="获取友联页面")
	public Result friends() {
		List<Friend> friendList = friendService.getFriendVOList();
		FriendInfo friendInfo = friendService.getFriendInfo(true, true);
		Map<String, Object> map = new HashMap<>(4);
		map.put("friendList", friendList);
		map.put("friendInfo", friendInfo);
		return Result.ok("获取成功", map);
	}

	/**
	 * 按昵称增加友链浏览次数
	 *
	 * @param nickname 友链昵称
	 * @return
	 */
	@VisitLogger(VisitBehavior.CLICK_FRIEND)
	@PostMapping("/friend")
	@Operation(description ="按昵称增加友链浏览次数")
	public Result addViews(@Parameter(description = "友链昵称") @RequestParam String nickname) {
		friendService.updateViewsByNickname(nickname);
		return Result.ok("请求成功");
	}
}
