package com.chaobk.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @Description: 用户实体类
 * @Author: Naccl
 * @Date: 2020-07-19
 */

@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "用户实体类")
public class User implements UserDetails {
	@Schema(description = "用户ID")
	private Long id;
	@Schema(description = "用户名")
	private String username;
	@Schema(description = "密码")
	private String password;
	@Schema(description =  "昵称", hidden = true)
	private String nickname;
	@Schema(description =  "头像", hidden = true)
	private String avatar;
	@Schema(description =  "邮箱", hidden = true)
	private String email;
	@Schema(description =  "创建时间", hidden = true)
	private Date createTime;
	@Schema(description =  "更新时间", hidden = true)
	private Date updateTime;
	@Schema(description =  "角色", hidden = true)
	private String role;

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorityList = new ArrayList<>();
		authorityList.add(new SimpleGrantedAuthority(role));
		return authorityList;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
