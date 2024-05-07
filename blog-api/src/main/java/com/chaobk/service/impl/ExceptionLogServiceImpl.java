package com.chaobk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaobk.entity.ExceptionLog;
import com.chaobk.exception.PersistenceException;
import com.chaobk.mapper.ExceptionLogMapper;
import com.chaobk.model.dto.UserAgentDTO;
import com.chaobk.service.ExceptionLogService;
import com.chaobk.util.IpAddressUtils;
import com.chaobk.util.UserAgentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 异常日志业务层实现
 * @Author: Naccl
 * @Date: 2020-12-03
 */
@Service
@RequiredArgsConstructor
public class ExceptionLogServiceImpl implements ExceptionLogService {
	private final ExceptionLogMapper exceptionLogMapper;
    private final UserAgentUtils userAgentUtils;

	@Override
	public List<ExceptionLog> getExceptionLogListByDate(String startDate, String endDate) {
		QueryWrapper<ExceptionLog> queryWrapper = new QueryWrapper<>();
		if (startDate!= null && endDate!= null) {
			queryWrapper.between("create_time", startDate, endDate);
		}
		return exceptionLogMapper.selectList(queryWrapper);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveExceptionLog(ExceptionLog log) {
		String ipSource = IpAddressUtils.getCityInfo(log.getIp());
		UserAgentDTO userAgentDTO = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
		log.setIpSource(ipSource);
		log.setOs(userAgentDTO.getOs());
		log.setBrowser(userAgentDTO.getBrowser());
		if (exceptionLogMapper.saveExceptionLog(log) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Override
	public void deleteExceptionLogById(Long id) {
		if (exceptionLogMapper.deleteById(id) != 1) {
			throw new PersistenceException("删除日志失败");
		}
	}
}
