package com.chaobk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaobk.entity.About;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 关于我持久层接口
 * @Author: Naccl
 * @Date: 2020-08-31
 */
@Mapper
@Repository
public interface AboutMapper extends BaseMapper<About> {

	int updateAbout(String nameEn, String value);

	String getAboutCommentEnabled();
}
