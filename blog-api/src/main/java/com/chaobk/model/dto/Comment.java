package com.chaobk.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema
public class Comment {
	private Long id;
	@Schema(description =  "昵称")
	private String nickname;
	@Schema(description =  "邮箱")
	private String email;
	@Schema(description =  "评论内容")
	private String content;
	@Schema(description =  "头像(图片路径)")
	private String avatar;
	@Schema(description =  "评论时间")
	private Date createTime;
	@Schema(description =  "个人网站")
	private String website;
	@Schema(description =  "评论者ip地址")
	private String ip;
	@Schema(description =  "是否公开")
	private Boolean published;
	@Schema(description =  "是否博主回复")
	private Boolean adminComment;
	@Schema(description =  "0普通文章，1关于我页面")
	private Integer page;
	@Schema(description =  "接收邮件提醒")
	private Boolean notice;
	@Schema(description =  "父评论id")
	private Long parentCommentId;
	@Schema(description =  "所属的文章id")
	private Long blogId;
	@Schema(description =  "QQ号，如果评论昵称为QQ号，则将昵称和头像置为QQ昵称和QQ头像，并将此字段置为QQ号备份")
	private String qq;
}
