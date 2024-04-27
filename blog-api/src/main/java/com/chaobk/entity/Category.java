package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 博客分类
 * @Author: Naccl
 * @Date: 2020-07-26
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Category {
	private Long id;
	@TableField(value = "category_name")
	private String name;//分类名称
	@TableField(exist = false)
	private List<Blog> blogs = new ArrayList<>();//该分类下的博客文章
}
