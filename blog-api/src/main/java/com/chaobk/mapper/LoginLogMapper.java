package com.chaobk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaobk.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 登录日志持久层接口
 * @Author: Naccl
 * @Date: 2020-12-03
 */
@Mapper
@Repository
public interface LoginLogMapper extends BaseMapper<LoginLog> {

	int saveLoginLog(LoginLog log);
}
