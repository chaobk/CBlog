package com.chaobk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chaobk.constant.RedisKeyConstants;
import com.chaobk.entity.Tag;
import com.chaobk.exception.NotFoundException;
import com.chaobk.exception.PersistenceException;
import com.chaobk.mapper.TagMapper;
import com.chaobk.service.RedisService;
import com.chaobk.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description: 博客标签业务层实现
 * @Author: Naccl
 * @Date: 2020-07-30
 */
@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
	private final TagMapper tagMapper;
	private final RedisService redisService;

	@Override
	public List<Tag> getTagList() {
		return tagMapper.selectList(null);
	}

	@Override
	public List<Tag> getTagListNotId() {
		String redisKey = RedisKeyConstants.TAG_CLOUD_LIST;
		List<Tag> tagListFromRedis = redisService.getListByValue(redisKey);
		if (tagListFromRedis != null) {
			return tagListFromRedis;
		}
		List<Tag> tagList = tagMapper.getTagListNotId();
		redisService.saveListToValue(redisKey, tagList);
		return tagList;
	}

	@Override
	public List<Tag> getTagListByBlogId(Long blogId) {
		return tagMapper.getTagListByBlogId(blogId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveTag(Tag tag) {
		if (tagMapper.saveTag(tag) != 1) {
			throw new PersistenceException("标签添加失败");
		}
		redisService.deleteCacheByKey(RedisKeyConstants.TAG_CLOUD_LIST);
	}

	@Override
	public Tag getTagById(Long id) {
		Tag tag = tagMapper.getTagById(id);
		if (tag == null) {
			throw new NotFoundException("标签不存在");
		}
		return tag;
	}

	@Override
	public Tag getTagByName(String name) {
		return tagMapper.selectOne(new QueryWrapper<Tag>().eq("name", name));
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteTagById(Long id) {
		if (tagMapper.deleteById(id) != 1) {
			throw new PersistenceException("标签删除失败");
		}
		redisService.deleteCacheByKey(RedisKeyConstants.TAG_CLOUD_LIST);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateTag(Tag tag) {
		if (tagMapper.updateTag(tag) != 1) {
			throw new PersistenceException("标签更新失败");
		}
		redisService.deleteCacheByKey(RedisKeyConstants.TAG_CLOUD_LIST);
		//修改了标签名或颜色，可能有首页文章关联了标签，也要更新首页缓存
		redisService.deleteCacheByKey(RedisKeyConstants.HOME_BLOG_INFO_LIST);
	}
}
