package com.tf.sdk.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用接口返回对象（用于对外接口）
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月10日
 */
@Getter
@Setter
public class ReceiveMsg implements Serializable{
	private static final long serialVersionUID = 8673722562349897258L;
	
	private int code;		// 错误码 0表示正确
	private String desc;	// 错误信息
	private String data;	// 具体内容
	private long timestamp;	// 时间戮
	
	public static Object createNew() {
		return new ReceiveMsg(0, "SUCC", null);
	}
	public static ReceiveMsg createNew(int code, String desc) {
		return new ReceiveMsg(code, desc, null);
	}
	
	public static ReceiveMsg createNew(int code, String desc, String data) {
		return new ReceiveMsg(code, desc, data);
	}
	
	public static ReceiveMsg createNew(String data) {
		return new ReceiveMsg(0, "SUCC", data);
	}
	
	
	private ReceiveMsg() {
		super();
	}
	private ReceiveMsg(int code, String desc, String data) {
		super();
		this.code = code;
		this.desc = desc;
		this.data = data;
		this.timestamp = System.currentTimeMillis();
	}


}


