package com.chaobk.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "博客可见性")
public class BlogVisibility {
	@ApiModelProperty(value = "赞赏开关", example = "true")
	private Boolean appreciation;//赞赏开关
	@ApiModelProperty(value = "推荐开关", example = "true")
	private Boolean recommend;//推荐开关
	@ApiModelProperty(value = "评论开关", example = "true")
	private Boolean commentEnabled;//评论开关
	@ApiModelProperty(value = "置顶", example = "true")
	private Boolean top;//是否置顶
	@ApiModelProperty(value = "公开或私密", example = "true")
	private Boolean published;//公开或私密
	@ApiModelProperty(value = "密码保护", example = "123456")
	private String password;//密码保护
}
