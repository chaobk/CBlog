package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "友链")
public class Friend {
	@TableId(type = IdType.AUTO)
	private Long id;
	@TableField("nickname")
	@Schema(description =  "昵称")
	private String nickname;
	@TableField("description")
	@Schema(description =  "描述")
	private String description;
	@TableField("website")
	@Schema(description =  "站点")
	private String website;
	@TableField("avatar")
	@Schema(description =  "头像")
	private String avatar;
	@TableField("is_published")
	@Schema(description =  "是否公开")
	private Boolean published;
	@TableField("views")
	@Schema(description =  "浏览次数")
	private Integer views;
	@TableField("create_time")
	@Schema(description =  "创建时间")
	private Date createTime;
}
