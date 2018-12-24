package com.tf.sdk.pojo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 通用返回对象（系统内使用）
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月9日
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Return<T> implements Serializable {
	private static final long serialVersionUID = 5821756912459726994L;
	
	public static <T> boolean isErr(Return<T> ret) {
		if (ret == null) {
			return true;
		}
		
		if (ret.getCode() != 0) {
			return true;
		}
		
		return false;
	}
	
	public static <T> Return<T> createNew() {
		return new Return<T>(0, "SUCC", null);
	}
	
	public static <T> Return<T> createNew(int code, String desc) {
		return new Return<T>(code, desc, null);
	}
	
	public static <T> Return<T> createNew(int code, String desc, T data) {
		return new Return<T>(code, desc, data);
	}
	
	public static <T> Return<T> createNew(T data) {
		return new Return<T>(0, "SUCC", data);
	}

	private int code;
	private String desc;
	private T data;
}


