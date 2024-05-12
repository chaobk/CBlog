package com.chaobk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaobk.entity.ScheduleJob;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 定时任务持久层接口
 * @Author: Naccl
 * @Date: 2020-11-01
 */
@Mapper
@Repository
public interface ScheduleJobMapper extends BaseMapper<ScheduleJob> {
	int updateJobStatusById(Long jobId, Boolean status);
}
