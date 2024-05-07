package com.chaobk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chaobk.constant.RedisKeyConstants;
import com.chaobk.constant.SiteSettingConstants;
import com.chaobk.entity.SiteSetting;
import com.chaobk.exception.PersistenceException;
import com.chaobk.mapper.FriendMapper;
import com.chaobk.mapper.SiteSettingMapper;
import com.chaobk.model.dto.Friend;
import com.chaobk.model.vo.FriendInfo;
import com.chaobk.service.FriendService;
import com.chaobk.service.RedisService;
import com.chaobk.util.markdown.MarkdownUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description: 友链业务层实现
 * @Author: Naccl
 * @Date: 2020-09-08
 */
@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {
	private final FriendMapper friendMapper;
	private final SiteSettingMapper siteSettingMapper;
	private final RedisService redisService;

	@Override
	public List<com.chaobk.entity.Friend> getFriendList() {
		return friendMapper.selectList(null);
	}

	@Override
	public List<com.chaobk.model.vo.Friend> getFriendVOList() {
		return friendMapper.getFriendVOList();
	}

	@Override
	public void updateFriendPublishedById(Long friendId, Boolean published) {
		com.chaobk.entity.Friend friend = com.chaobk.entity.Friend.builder().id(friendId).published(published).build();
		if (friendMapper.updateById(friend) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	@Override
	public void saveFriend(com.chaobk.entity.Friend friend) {
		friend.setViews(0);
		friend.setCreateTime(new Date());
		if (friendMapper.insert(friend) != 1) {
			throw new PersistenceException("添加失败");
		}
	}

	@Override
	public void updateFriend(Friend friend) {
		if (friendMapper.updateFriend(friend) != 1) {
			throw new PersistenceException("修改失败");
		}
	}

	@Override
	public void deleteFriend(Long id) {
		if (friendMapper.deleteById(id) != 1) {
			throw new PersistenceException("删除失败");
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateViewsByNickname(String nickname) {
		if (friendMapper.updateViewsByNickname(nickname) != 1) {
			throw new PersistenceException("操作失败");
		}
	}

	/**
	 *
	 * @param cache
	 * @param md
	 * @return
	 */
	@Override
	public FriendInfo getFriendInfo(boolean cache, boolean md) {
		String redisKey = RedisKeyConstants.FRIEND_INFO_MAP;
		if (cache) {
			FriendInfo friendInfoFromRedis = redisService.getObjectByValue(redisKey, FriendInfo.class);
			if (friendInfoFromRedis != null) {
				return friendInfoFromRedis;
			}
		}
		List<SiteSetting> siteSettings = siteSettingMapper.selectList(new QueryWrapper<SiteSetting>().eq("type", SiteSettingConstants.TYPE_4));

		FriendInfo friendInfo = new FriendInfo();
		for (SiteSetting siteSetting : siteSettings) {
			if (SiteSettingConstants.FRIEND_CONTENT.equals(siteSetting.getNameEn())) {
				if (md) {
					friendInfo.setContent(MarkdownUtils.markdownToHtmlExtensions(siteSetting.getValue()));
				} else {
					friendInfo.setContent(siteSetting.getValue());
				}
			} else if (SiteSettingConstants.FRIEND_COMMENT_ENABLED.equals(siteSetting.getNameEn())) {
				friendInfo.setCommentEnabled(SiteSettingConstants.FRIEND_COMMENT_ENABLED_YES.equals(siteSetting.getValue()));
			}
		}
		if (cache && md) {
			redisService.saveObjectToValue(redisKey, friendInfo);
		}
		return friendInfo;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateFriendInfoContent(String content) {
		UpdateWrapper<SiteSetting> wrapper = new UpdateWrapper<SiteSetting>().set("value", content).eq("name_en", "friendContent");
		if (siteSettingMapper.update(wrapper) != 1) {
			throw new PersistenceException("修改失败");
		}
		deleteFriendInfoRedisCache();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateFriendInfoCommentEnabled(Boolean commentEnabled) {
		UpdateWrapper<SiteSetting> updateWrapper = new UpdateWrapper<>();
		updateWrapper.set("value", commentEnabled);
		updateWrapper.eq("name_en", "friendCommentEnabled");
		if (siteSettingMapper.update(updateWrapper) != 1) {
			throw new PersistenceException("修改失败");
		}
		deleteFriendInfoRedisCache();
	}

	/**
	 * 删除友链页面缓存
	 */
	private void deleteFriendInfoRedisCache() {
		redisService.deleteCacheByKey(RedisKeyConstants.FRIEND_INFO_MAP);
	}
}
