package com.his.common.mq.elastic;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 引擎发送参数
 * @author chenjx
 * @Version 1.1.0
 * @time   2018年10月27日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ElasticPojo implements Serializable {
	private static final long serialVersionUID = 3359822079711538908L;

	/**
	 * 操作类型（增加、修改、删除）
	 */
	private int operatorType;
	
	/**
	 * 数据类型（药品、项目等）
	 */
	private int dataType;
	
	private long id;
	
	private long clinicId;
	
	private String word;
}
