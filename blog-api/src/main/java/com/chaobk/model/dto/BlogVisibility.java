package com.chaobk.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: 博客可见性DTO
 * @Author: Naccl
 * @Date: 2020-09-04
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Schema(description = "博客可见性")
public class BlogVisibility {
	@Schema(description =  "赞赏开关", example = "true")
	private Boolean appreciation;//赞赏开关
	@Schema(description =  "推荐开关", example = "true")
	private Boolean recommend;//推荐开关
	@Schema(description =  "评论开关", example = "true")
	private Boolean commentEnabled;//评论开关
	@Schema(description =  "置顶", example = "true")
	private Boolean top;//是否置顶
	@Schema(description =  "公开或私密", example = "true")
	private Boolean published;//公开或私密
	@Schema(description =  "密码保护", example = "123456")
	private String password;//密码保护
}
