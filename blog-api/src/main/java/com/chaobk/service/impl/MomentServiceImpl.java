package com.chaobk.service.impl;

import com.chaobk.entity.Moment;
import com.chaobk.exception.NotFoundException;
import com.chaobk.exception.PersistenceException;
import com.chaobk.mapper.MomentMapper;
import com.chaobk.service.MomentService;
import com.chaobk.util.markdown.MarkdownUtils;
import com.github.pagehelper.PageHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 博客动态业务层实现
 * @Author: Naccl
 * @Date: 2020-08-24
 */
@Service
@RequiredArgsConstructor
public class MomentServiceImpl implements MomentService {
	private final MomentMapper momentMapper;
	//每页显示5条动态
	private static final int pageSize = 5;
	//动态列表排序方式
	private static final String orderBy = "create_time desc";
	//私密动态提示
	private static final String PRIVATE_MOMENT_CONTENT = "<p>此条为私密动态，仅发布者可见！</p>";

	@Override
	public List<Moment> getMomentList() {
		return momentMapper.selectList(null);
	}

	@Override
	public List<Moment> getMomentVOList(Integer pageNum, boolean adminIdentity) {
		PageHelper.startPage(pageNum, pageSize, orderBy);
		List<Moment> moments = momentMapper.selectList(null);
		for (Moment moment : moments) {
			if (adminIdentity || moment.getPublished()) {
				moment.setContent(MarkdownUtils.markdownToHtmlExtensions(moment.getContent()));
			} else {
				moment.setContent(PRIVATE_MOMENT_CONTENT);
			}
		}
		return moments;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addLikeByMomentId(Long momentId) {
		if (momentMapper.addLikeByMomentId(momentId) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	@Override
	public void updateMomentPublishedById(Long momentId, Boolean published) {
		Moment moment = Moment.builder().id(momentId).published(published).build();
		if (momentMapper.updateById(moment) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	@Override
	public Moment getMomentById(Long id) {
		Moment moment = momentMapper.selectById(id);
		if (moment == null) {
			throw new NotFoundException("动态不存在");
		}
		return moment;
	}

	@Override
	public void deleteMomentById(Long id) {
		if (momentMapper.deleteById(id) != 1) {
			throw new PersistenceException("删除失败");
		}
	}

	@Override
	public void saveMoment(Moment moment) {
		if (momentMapper.insert(moment) != 1) {
			throw new PersistenceException("动态添加失败");
		}
	}

	@Override
	public void updateMoment(Moment moment) {
		if (momentMapper.updateById(moment) != 1) {
			throw new PersistenceException("动态修改失败");
		}
	}
}
