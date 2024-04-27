package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("博客分类")
public class Category {
	private Long id;
	@TableField(value = "category_name")
	@ApiModelProperty(value = "分类名称", example = "编程")
	private String name;//分类名称
	@TableField(exist = false)
	private List<Blog> blogs = new ArrayList<>();//该分类下的博客文章
}
