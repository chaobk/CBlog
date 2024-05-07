package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.Date;

/**
 * @Description: 友链
 * @Author: Naccl
 * @Date: 2020-09-08
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@TableName("friend")
@ApiModel("友链")
public class Friend {
	@TableId(type = IdType.AUTO)
	private Long id;
	@TableField("nickname")
	@ApiModelProperty(value = "昵称")
	private String nickname;
	@TableField("description")
	@ApiModelProperty(value = "描述")
	private String description;
	@TableField("website")
	@ApiModelProperty(value = "站点")
	private String website;
	@TableField("avatar")
	@ApiModelProperty(value = "头像")
	private String avatar;
	@TableField("is_published")
	@ApiModelProperty(value = "是否公开")
	private Boolean published;
	@TableField("views")
	@ApiModelProperty(value = "浏览次数")
	private Integer views;
	@TableField("create_time")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
}
