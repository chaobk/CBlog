package com.chaobk.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @Description: 评论DTO
 * @Author: Naccl
 * @Date: 2020-08-27
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel
public class Comment {
	private Long id;
	@ApiModelProperty(value = "昵称")
	private String nickname;
	@ApiModelProperty(value = "邮箱")
	private String email;
	@ApiModelProperty(value = "评论内容")
	private String content;
	@ApiModelProperty(value = "头像(图片路径)")
	private String avatar;
	@ApiModelProperty(value = "评论时间")
	private Date createTime;
	@ApiModelProperty(value = "个人网站")
	private String website;
	@ApiModelProperty(value = "评论者ip地址")
	private String ip;
	@ApiModelProperty(value = "是否公开")
	private Boolean published;
	@ApiModelProperty(value = "是否博主回复")
	private Boolean adminComment;
	@ApiModelProperty(value = "0普通文章，1关于我页面")
	private Integer page;
	@ApiModelProperty(value = "接收邮件提醒")
	private Boolean notice;
	@ApiModelProperty(value = "父评论id")
	private Long parentCommentId;
	@ApiModelProperty(value = "所属的文章id")
	private Long blogId;
	@ApiModelProperty(value = "QQ号，如果评论昵称为QQ号，则将昵称和头像置为QQ昵称和QQ头像，并将此字段置为QQ号备份")
	private String qq;
}
