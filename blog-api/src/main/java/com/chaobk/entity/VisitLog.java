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
 * @Description: 访问日志
 * @Author: Naccl
 * @Date: 2020-12-04
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@TableName("visit_log")
public class VisitLog {
	@TableId(type = IdType.AUTO)
	private Long id;
	@TableField("uuid")
	private String uuid;//访客标识码
	@TableField("uri")
	private String uri;//请求接口
	@TableField("method")
	private String method;//请求方式
	@TableField("param")
	private String param;//请求参数
	@TableField("behavior")
	private String behavior;//访问行为
	@TableField("content")
	private String content;//访问内容
	@TableField("remark")
	private String remark;//备注
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
	private Date createTime;//访问时间
	@TableField("user_agent")
	private String userAgent;

	public VisitLog(String uuid, String uri, String method, String behavior, String content, String remark, String ip, Integer times, String userAgent) {
		this.uuid = uuid;
		this.uri = uri;
		this.method = method;
		this.behavior = behavior;
		this.content = content;
		this.remark = remark;
		this.ip = ip;
		this.times = times;
		this.createTime = new Date();
		this.userAgent = userAgent;
	}
}
