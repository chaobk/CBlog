package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * @Description: 操作日志
 * @Author: Naccl
 * @Date: 2020-11-30
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@TableName("operation_log")
public class OperationLog {
	@TableId(type = IdType.AUTO)
	private Long id;
	@TableField("username")
	private String username;//操作者用户名
	@TableField("uri")
	private String uri;//请求接口
	@TableField("method")
	private String method;//请求方式
	@TableField("param")
	private String param;//请求参数
	@TableField("description")
	private String description;//操作描述
	@TableField("ip")
	private String ip;//ip
	@TableField("ip_source")
	private String ipSource;//ip来源
	@TableField("os")
	private String os;//操作系统
	@TableField("browser")
	private String browser;//浏览器
	@TableField("times")
	private Integer times;//请求耗时（毫秒）
	@TableField("create_time")
	private Date createTime;//操作时间
	@TableField("user_agent")
	private String userAgent;

	public OperationLog(String username, String uri, String method, String description, String ip, Integer times, String userAgent) {
		this.username = username;
		this.uri = uri;
		this.method = method;
		this.description = description;
		this.ip = ip;
		this.times = times;
		this.createTime = new Date();
		this.userAgent = userAgent;
	}
}
