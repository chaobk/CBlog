package com.chaobk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chaobk.entity.Moment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 博客动态持久层接口
 * @Author: Naccl
 * @Date: 2020-08-24
 */
@Mapper
@Repository
public interface MomentMapper extends BaseMapper<Moment> {
	List<Moment> getMomentList();

	int addLikeByMomentId(Long momentId);

	int updateMomentPublishedById(Long momentId, Boolean published);

	Moment getMomentById(Long id);

	int deleteMomentById(Long id);

	int saveMoment(Moment moment);

	int updateMoment(Moment moment);
}
