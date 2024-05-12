package com.chaobk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chaobk.entity.ScheduleJob;
import com.chaobk.entity.ScheduleJobLog;
import com.chaobk.exception.PersistenceException;
import com.chaobk.mapper.ScheduleJobLogMapper;
import com.chaobk.mapper.ScheduleJobMapper;
import com.chaobk.service.ScheduleJobService;
import com.chaobk.util.quartz.ScheduleUtils;
import lombok.RequiredArgsConstructor;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Description: 定时任务业务层实现
 * @Author: Naccl
 * @Date: 2020-11-01
 */
@Service
@RequiredArgsConstructor
public class ScheduleJobServiceImpl implements ScheduleJobService {
	private final Scheduler scheduler;
	private final ScheduleJobMapper schedulerJobMapper;
	private final ScheduleJobLogMapper scheduleJobLogMapper;

	/**
	 * 项目启动时，初始化定时器
	 */
	@PostConstruct
	public void init() {
		List<ScheduleJob> scheduleJobList = getJobList();
		for (ScheduleJob scheduleJob : scheduleJobList) {
			CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getJobId());
			//如果不存在，则创建
			if (cronTrigger == null) {
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
			} else {
				ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
			}
		}
	}

	@Override
	public List<ScheduleJob> getJobList() {
		return schedulerJobMapper.selectList(null);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveJob(ScheduleJob scheduleJob) {
		if (schedulerJobMapper.insert(scheduleJob) != 1) {
			throw new PersistenceException("添加失败");
		}
		ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateJob(ScheduleJob scheduleJob) {
		if (schedulerJobMapper.updateById(scheduleJob) != 1) {
			throw new PersistenceException("更新失败");
		}
		ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteJobById(Long jobId) {
		ScheduleUtils.deleteScheduleJob(scheduler, jobId);
		if (schedulerJobMapper.deleteById(jobId) != 1) {
			throw new PersistenceException("删除失败");
		}
	}

	@Override
	public void runJobById(Long jobId) {
		ScheduleUtils.run(scheduler, schedulerJobMapper.selectById(jobId));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateJobStatusById(Long jobId, Boolean status) {
		if (status) {
			ScheduleUtils.resumeJob(scheduler, jobId);
		} else {
			ScheduleUtils.pauseJob(scheduler, jobId);
		}
		UpdateWrapper<ScheduleJob> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("status", status);
		updateWrapper.eq("job_id", jobId);
		if (schedulerJobMapper.update(updateWrapper) != 1) {
			throw new PersistenceException("修改失败");
		}
	}

	@Override
	public List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate) {
		QueryWrapper<ScheduleJobLog> queryWrapper = new QueryWrapper<>();
		if (startDate != null && endDate != null) {
			queryWrapper.between("create_time", startDate, endDate);
		}
		return scheduleJobLogMapper.selectList(queryWrapper);
	}

	@Override
	public void saveJobLog(ScheduleJobLog jobLog) {
		if (scheduleJobLogMapper.saveJobLog(jobLog) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Override
	public void deleteJobLogByLogId(Long logId) {
		if (scheduleJobLogMapper.deleteById(logId) != 1) {
			throw new PersistenceException("日志删除失败");
		}
	}
}
