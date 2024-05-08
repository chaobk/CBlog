package com.chaobk.controller.admin;

import com.chaobk.annotation.OperationLogger;
import com.chaobk.entity.Moment;
import com.chaobk.model.vo.Result;
import com.chaobk.service.MomentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Description: 博客动态后台管理
 * @Author: Naccl
 * @Date: 2020-08-24
 */
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Api(tags = "MomentAdminController - 博客动态后台管理")
public class MomentAdminController {
	private final MomentService momentService;

	/**
	 * 分页查询动态列表
	 *
	 * @param pageNum  页码
	 * @param pageSize 每页条数
	 * @return
	 */
	@GetMapping("/moments")
	@ApiOperation("查询动态列表")
	public Result moments(@RequestParam(defaultValue = "1") Integer pageNum,
	                      @RequestParam(defaultValue = "10") Integer pageSize) {
		String orderBy = "create_time desc";
		PageHelper.startPage(pageNum, pageSize, orderBy);
		PageInfo<Moment> pageInfo = new PageInfo<>(momentService.getMomentList());
		return Result.ok("请求成功", pageInfo);
	}

	/**
	 * 更新动态公开状态
	 *
	 * @param id        动态id
	 * @param published 是否公开
	 * @return
	 */
	@OperationLogger("更新动态公开状态")
	@PutMapping("/moment/published")
	@ApiOperation("更新动态公开状态")
	public Result updatePublished( @RequestParam Long id, @RequestParam Boolean published) {
		momentService.updateMomentPublishedById(id, published);
		return Result.ok("操作成功");
	}

	/**
	 * 根据id查询动态
	 *
	 * @param id 动态id
	 * @return
	 */
	@GetMapping("/moment")
	@ApiOperation("根据id查询动态")
	public Result moment(@RequestParam Long id) {
		return Result.ok("获取成功", momentService.getMomentById(id));
	}

	/**
	 * 删除动态
	 *
	 * @param id 动态id
	 * @return
	 */
	@OperationLogger("删除动态")
	@DeleteMapping("/moment")
	@ApiOperation("删除动态")
	public Result deleteMoment(@RequestParam Long id) {
		momentService.deleteMomentById(id);
		return Result.ok("删除成功");
	}

	/**
	 * 发布动态
	 *
	 * @param moment 动态实体
	 * @return
	 */
	@OperationLogger("发布动态")
	@PostMapping("/moment")
	@ApiOperation("发布动态")
	public Result saveMoment(@RequestBody Moment moment) {
		if (moment.getCreateTime() == null) {
			moment.setCreateTime(new Date());
		}
		momentService.saveMoment(moment);
		return Result.ok("添加成功");
	}

	/**
	 * 更新动态
	 *
	 * @param moment 动态实体
	 * @return
	 */
	@OperationLogger("更新动态")
	@PutMapping("/moment")
	@ApiOperation("更新动态")
	public Result updateMoment(@RequestBody Moment moment) {
		if (moment.getCreateTime() == null) {
			moment.setCreateTime(new Date());
		}
		momentService.updateMoment(moment);
		return Result.ok("修改成功");
	}
}
