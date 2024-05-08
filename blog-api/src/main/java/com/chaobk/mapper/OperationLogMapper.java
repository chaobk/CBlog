package com.chaobk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaobk.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 操作日志持久层接口
 * @Author: Naccl
 * @Date: 2020-11-30
 */
@Mapper
@Repository
public interface OperationLogMapper extends BaseMapper<OperationLog> {
	List<OperationLog> getOperationLogListByDate(String startDate, String endDate);

	int saveOperationLog(OperationLog log);

	int deleteOperationLogById(Long id);
}
