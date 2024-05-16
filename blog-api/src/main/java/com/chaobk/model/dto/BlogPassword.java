package com.chaobk.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "受保护文章密码")
public class BlogPassword {
	@Schema(description =  "文章ID", example = "1")
	private Long blogId;
	@Schema(description =  "文章密码", example = "123")
	private String password;
}
