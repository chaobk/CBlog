package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 博客标签
 * @Author: Naccl
 * @Date: 2020-07-27
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@TableName("tag")
public class Tag {
	@TableId("id")
	private Long id;
	@TableField("tag_name")
	private String name;//标签名称
	@TableField("color")
	private String color;//标签颜色(与Semantic UI提供的颜色对应，可选)
	@TableField(exist = false)
	private List<Blog> blogs = new ArrayList<>();//该标签下的博客文章
}
