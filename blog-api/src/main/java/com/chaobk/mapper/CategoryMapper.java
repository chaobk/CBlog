package com.chaobk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaobk.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 博客分类持久层接口
 * @Author: Naccl
 * @Date: 2020-07-29
 */
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {
	List<Category> getCategoryList();

	List<Category> getCategoryNameList();

	Category getCategoryById(Long id);

	int deleteCategoryById(Long id);
}
