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
 * @Description: 定时任务日志
 * @Author: Naccl
 * @Date: 2020-11-01
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@TableName("schedule_job_log")
public class ScheduleJobLog {
	@TableId(value = "log_id", type = IdType.AUTO)
	private Long logId;//日志id
	@TableField("job_id")
	private Long jobId;//任务id
	@TableField("bean_name")
	private String beanName;//spring bean名称
	@TableField("method_name")
	private String methodName;//方法名
	@TableField("params")
	private String params;//参数
	@TableField("status")
	private Boolean status;//任务执行结果
	@TableField("error")
	private String error;//异常信息
	@TableField("times")
	private Integer times;//耗时(单位：毫秒)
	@TableField("create_time")
	private Date createTime;//创建时间
}
