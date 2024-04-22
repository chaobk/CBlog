package com.chaobk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaobk.entity.User;
import com.chaobk.exception.NotFoundException;
import com.chaobk.mapper.UserMapper;
import com.chaobk.service.UserService;
import com.chaobk.util.HashUtils;
import com.chaobk.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @Description: 用户业务层接口实现类
 * @Author: Naccl
 * @Date: 2020-07-19
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserMapper userMapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		return user;
	}

	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		if (!HashUtils.matchBC(password, user.getPassword())) {
			throw new UsernameNotFoundException("密码错误");
		}
		return user;
	}

	@Override
	public User findUserById(Long id) {
		User user = userMapper.findById(id);
		if (user == null) {
			throw new NotFoundException("用户不存在");
		}
		return user;
	}

	@Override
	public boolean changeAccount(User user, String jwt) {
		String username = JwtUtils.getTokenBody(jwt).getSubject();
		user.setPassword(HashUtils.getBC(user.getPassword()));
		if (userMapper.updateUserByUsername(username, user) != 1) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}
}
