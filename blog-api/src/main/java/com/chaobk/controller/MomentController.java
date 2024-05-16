package com.chaobk.controller;

import com.chaobk.annotation.AccessLimit;
import com.chaobk.annotation.VisitLogger;
import com.chaobk.constant.JwtConstants;
import com.chaobk.entity.Moment;
import com.chaobk.entity.User;
import com.chaobk.enums.VisitBehavior;
import com.chaobk.model.vo.PageResult;
import com.chaobk.model.vo.Result;
import com.chaobk.service.MomentService;
import com.chaobk.service.impl.UserServiceImpl;
import com.chaobk.util.JwtUtils;
import com.github.pagehelper.PageInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 动态
 * @Author: Naccl
 * @Date: 2020-08-25
 */
@RestController
@RequiredArgsConstructor
@Tag(name = "MomentController - 动态")
public class MomentController {
	private final MomentService momentService;
    private final UserServiceImpl userService;

	@VisitLogger(VisitBehavior.MOMENT)
	@GetMapping("/moments")
	@Operation(description ="分页查询动态")
	public Result moments(@Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
                           @Parameter(description = "博主访问Token") @RequestHeader(value = "Authorization", defaultValue = "") String jwt) {
		boolean adminIdentity = false;
		if (JwtUtils.judgeTokenIsExist(jwt)) {
			try {
				String subject = JwtUtils.getTokenBody(jwt).getSubject();
				if (subject.startsWith(JwtConstants.ADMIN_PREFIX)) {
					//博主身份Token
					String username = subject.replace(JwtConstants.ADMIN_PREFIX, "");
					User admin = (User) userService.loadUserByUsername(username);
					if (admin != null) {
						adminIdentity = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PageInfo<Moment> pageInfo = new PageInfo<>(momentService.getMomentVOList(pageNum, adminIdentity));
		PageResult<Moment> pageResult = new PageResult<>(pageInfo.getPages(), pageInfo.getList());
		return Result.ok("获取成功", pageResult);
	}

	/**
	 * 给动态点赞
	 * 简单限制一下点赞
	 *
	 * @param id 动态id
	 * @return
	 */
	@AccessLimit(seconds = 86400, maxCount = 1, msg = "不可以重复点赞哦")
	@VisitLogger(VisitBehavior.LIKE_MOMENT)
	@PostMapping("/moment/like/{id}")
	@Operation(description ="给动态点赞")
	public Result like(@PathVariable Long id) {
		momentService.addLikeByMomentId(id);
		return Result.ok("点赞成功");
	}
}
