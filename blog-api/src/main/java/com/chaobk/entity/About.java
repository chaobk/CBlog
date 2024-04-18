package com.chaobk.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description: 关于我
 * @Author: Naccl
 * @Date: 2020-08-31
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@TableName("about")
public class About {
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;
	private String nameEn;
	private String nameZh;
	private String value;
}
