package com.chaobk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaobk.entity.LoginLog;
import com.chaobk.exception.PersistenceException;
import com.chaobk.mapper.LoginLogMapper;
import com.chaobk.model.dto.UserAgentDTO;
import com.chaobk.service.LoginLogService;
import com.chaobk.util.IpAddressUtils;
import com.chaobk.util.UserAgentUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 登录日志业务层实现
 * @Author: Naccl
 * @Date: 2020-12-03
 */
@Service
@RequiredArgsConstructor
public class LoginLogServiceImpl implements LoginLogService {
	private final LoginLogMapper loginLogMapper;
    private final UserAgentUtils userAgentUtils;

	@Override
	public List<LoginLog> getLoginLogListByDate(String startDate, String endDate) {
		QueryWrapper<LoginLog> wrapper = new QueryWrapper<>();
		if (startDate!= null && endDate!= null) {
			wrapper.between("create_time", startDate, endDate);
		}
		return loginLogMapper.selectList(wrapper);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveLoginLog(LoginLog log) {
		String ipSource = IpAddressUtils.getCityInfo(log.getIp());
		UserAgentDTO userAgentDTO = userAgentUtils.parseOsAndBrowser(log.getUserAgent());
		log.setIpSource(ipSource);
		log.setOs(userAgentDTO.getOs());
		log.setBrowser(userAgentDTO.getBrowser());
		if (loginLogMapper.saveLoginLog(log) != 1) {
			throw new PersistenceException("日志添加失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteLoginLogById(Long id) {
		if (loginLogMapper.deleteById(id) != 1) {
			throw new PersistenceException("删除日志失败");
		}
	}
}
