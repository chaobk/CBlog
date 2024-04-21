package com.chaobk.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@ApiModel("受保护文章密码")
public class BlogPassword {
	@ApiModelProperty(value = "文章ID", example = "1")
	private Long blogId;
	@ApiModelProperty(value = "文章密码", example = "123")
	private String password;
}
