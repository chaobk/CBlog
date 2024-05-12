package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chaobk.model.vo.BlogIdAndTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 博客评论
 * @Author: Naccl
 * @Date: 2020-07-27
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@TableName("comment")
public class Comment {
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	@TableField("nickname")
	private String nickname;//昵称
	@TableField("email")
	private String email;//邮箱
	@TableField("content")
	private String content;//评论内容
	@TableField("avatar")
	private String avatar;//头像(图片路径)
	@TableField("create_time")
	private Date createTime;//评论时间
	@TableField("website")
	private String website;//个人网站
	@TableField("ip")
	private String ip;//评论者ip地址
	@TableField("is_published")
	private Boolean published;//是否公开
	@TableField("is_admin_comment")
	private Boolean adminComment;//博主回复
	@TableField("page")
	private Integer page;//0普通文章，1关于我页面
	@TableField("is_notice")
	private Boolean notice;//接收邮件提醒
	@TableField("parent_comment_id")
	private Long parentCommentId;//父评论id
	@TableField("qq")
	private String qq;//如果评论昵称为QQ号，则将昵称和头像置为QQ昵称和QQ头像，并将此字段置为QQ号备份

	@TableField(exist = false)
	private BlogIdAndTitle blog;//所属的文章
	@TableField(exist = false)
	private List<Comment> replyComments = new ArrayList<>();//回复该评论的评论
}
