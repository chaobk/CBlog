package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 博客文章
 * @Author: Naccl
 * @Date: 2020-07-26
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@TableName("blog")
public class Blog {
	@TableField("id")
	private Long id;
	@TableField("title")
	private String title;//文章标题
	@TableField("first_picture")
	private String firstPicture;//文章首图，用于随机文章展示
	@TableField("content")
	private String content;//文章正文
	@TableField("description")
	private String description;//描述
	@TableField("is_published")
	private Boolean published;//公开或私密
	@TableField("is_recommend")
	private Boolean recommend;//推荐开关
	@TableField("is_appreciation")
	private Boolean appreciation;//赞赏开关
	@TableField("is_comment_enabled")
	private Boolean commentEnabled;//评论开关
	@TableField("is_top")
	private Boolean top;//是否置顶
	@TableField("create_time")
	private Date createTime;//创建时间
	@TableField("update_time")
	private Date updateTime;//更新时间
	@TableField("views")
	private Integer views;//浏览次数
	@TableField("words")
	private Integer words;//文章字数
	@TableField("read_time")
	private Integer readTime;//阅读时长(分钟)
	@TableField("password")
	private String password;//密码保护

	@TableField(exist = false)
	private User user;//文章作者(因为是个人博客，也可以不加作者字段，暂且加上)
	@TableField(exist = false)
	private Category category;//文章分类
	@TableField(exist = false)
	private List<Tag> tags = new ArrayList<>();//文章标签
}
