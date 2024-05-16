package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: 定时任务
 * @Author: Naccl
 * @Date: 2020-11-01
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "定时任务")
@TableName("schedule_job")
public class ScheduleJob {
	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY"; 
	@Schema(description = "任务id")
	@TableId(value = "job_id", type = IdType.AUTO)
	private Long jobId;

	@Schema(description = "spring bean名称")
	@TableField("bean_name")
	private String beanName;

	@Schema(description = "方法名")
	@TableField("method_name")
	private String methodName;

	@Schema(description = "参数")
	@TableField("params")
	private String params;

	@Schema(description = "cron表达式")
	@TableField("cron")
	private String cron;
	
	@Schema(description = "任务状态")
	@TableField("status")
	private Boolean status;
	
	@Schema(description = "备注")
	@TableField("remark")
	private String remark;
	
	@Schema(description = "创建时间")
	@TableField("create_time")
	private Date createTime;
}
