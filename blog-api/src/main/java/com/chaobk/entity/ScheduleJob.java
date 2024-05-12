package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "定时任务")
@TableName("schedule_job")
public class ScheduleJob {
	public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY"; 
	@ApiModelProperty("任务id")
	@TableId(value = "job_id", type = IdType.AUTO)
	private Long jobId;

	@ApiModelProperty("spring bean名称")
	@TableField("bean_name")
	private String beanName;

	@ApiModelProperty("方法名")
	@TableField("method_name")
	private String methodName;

	@ApiModelProperty("参数")
	@TableField("params")
	private String params;

	@ApiModelProperty("cron表达式")
	@TableField("cron")
	private String cron;
	
	@ApiModelProperty("任务状态")
	@TableField("status")
	private Boolean status;
	
	@ApiModelProperty("备注")
	@TableField("remark")
	private String remark;
	
	@ApiModelProperty("创建时间")
	@TableField("create_time")
	private Date createTime;
}
