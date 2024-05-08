package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: 登录日志
 * @Author: Naccl
 * @Date: 2020-12-03
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@TableName("login_log")
public class LoginLog {
	@TableId(type = IdType.AUTO)
	private Long id;
	@TableField("username")
	private String username;//用户名称
	@TableField("ip")
	private String ip;//ip
	@TableField("ip_source")
	private String ipSource;//ip来源
	@TableField("os")
	private String os;//操作系统
	@TableField("browser")
	private String browser;//浏览器
	@TableField("status")
	private Boolean status;//登录状态
	@TableField("description")
	private String description;//操作信息
	@TableField("create_time")
	private Date createTime;//操作时间
	@TableField("user_agent")
	private String userAgent;

	public LoginLog(String username, String ip, boolean status, String description, String userAgent) {
		this.username = username;
		this.ip = ip;
		this.status = status;
		this.description = description;
		this.createTime = new Date();
		this.userAgent = userAgent;
	}
}