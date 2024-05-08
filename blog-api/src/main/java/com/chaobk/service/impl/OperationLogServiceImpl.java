package com.chaobk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaobk.entity.OperationLog;
import com.chaobk.exception.PersistenceException;
import com.chaobk.mapper.OperationLogMapper;
import com.chaobk.model.dto.UserAgentDTO;
import com.chaobk.service.OperationLogService;
import com.chaobk.util.IpAddressUtils;
import com.chaobk.util.UserAgentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 操作日志业务层实现
 * @Author: Naccl
 * @Date: 2020-11-30
 */
@Service
@RequiredArgsConstructor
public class OperationLogServiceImpl implements OperationLogService {
	private final OperationLogMapper operationLogMapper;
    private final UserAgentUtils userAgentUtils;

	@Override
	public List<OperationLog> getOperationLogListByDate(String startDate, String endDate) {
		QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();
		if (startDate!= null && endDate!= null) {
			queryWrapper.between("create_time", startDate, endDate);
		}
		return operationLogMapper.selectList(queryWrapper);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveOperationLog(OperationLog log) {
		String ipSource = IpAddressUtils.getCityInfo(log.getIp());
		UserAgentDTO userAgentDTO = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
		log.setIpSource(ipSource);
		log.setOs(userAgentDTO.getOs());
		log.setBrowser(userAgentDTO.getBrowser());
		if (operationLogMapper.saveOperationLog(log) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Override
	public void deleteOperationLogById(Long id) {
		if (operationLogMapper.deleteById(id) != 1) {
			throw new PersistenceException("删除日志失败");
		}
	}
}
