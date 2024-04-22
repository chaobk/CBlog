package com.chaobk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaobk.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 用户持久层接口
 * @Author: Naccl
 * @Date: 2020-07-19
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
	User findById(Long id);

	int updateUserByUsername(String username, User user);
}
