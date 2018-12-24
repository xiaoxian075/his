package com.his.common.mq.chit;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChitPojo implements Serializable {
	private static final long serialVersionUID = 7290974776947888314L;

	/**
	 * 类型
	 */
	private int type;
	
	/**
	 * 手机号
	 */
	private String[] phones;
	
	/**
	 * 参数列表
	 */
	private String[] args;
}
