package com.chaobk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaobk.entity.ScheduleJobLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 定时任务日志持久层接口
 * @Author: Naccl
 * @Date: 2020-11-01
 */
@Mapper
@Repository
public interface ScheduleJobLogMapper extends BaseMapper<ScheduleJobLog> {
	List<ScheduleJobLog> getJobLogListByDate(String startDate, String endDate);

	int saveJobLog(ScheduleJobLog jobLog);

	int deleteJobLogByLogId(Long logId);
}
