package com.his.common.net;

import java.util.List;

import com.his.common.define.EnumSessionError;
import com.tf.sdk.pojo.ReceiveMsg;
import com.tf.sdk.pojo.Return;
import com.tf.sdk.pojo.TFPageInfo;
import com.tf.sdk.serializable.GsonUtil;


/**
 * 全局公用操作函数
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月10日
 */
public class Constant {
	
	// pack为打包类
	
	private final static String STR_ZERO_PACK = GsonUtil.toString(ReceiveMsg.createNew());
	public static String pack() {
		return STR_ZERO_PACK;
	}
	
	public static String pack(int code, String desc) {
		return GsonUtil.toString(ReceiveMsg.createNew(code, desc));
	}
	public static <T> String pack(int code, String desc, T t) {
		return GsonUtil.toString(ReceiveMsg.createNew(code, desc, GsonUtil.toString(t)));
	}
	
	public static <T> String pack(T t) {
		return GsonUtil.toString(ReceiveMsg.createNew(GsonUtil.toString(t)));
	}

	public static String pack(EnumSessionError error) {
		return GsonUtil.toString(ReceiveMsg.createNew(error.getCode(), error.getDesc()));
	}
	public static String pack(EnumSessionError error,Object o) {
		return GsonUtil.toString(ReceiveMsg.createNew(error.getCode(), error.getDesc(), GsonUtil.toString(o)));
	}
	
	public static <T> String pack(Return<T> r) {
		if (Return.isErr(r)) {
			return GsonUtil.toString(ReceiveMsg.createNew(r.getCode(), r.getDesc()));
		}
		return GsonUtil.toString(ReceiveMsg.createNew(GsonUtil.toString(r.getData())));
	}
	
	public static <T> String packList(Return<List<T>> r) {
		if (Return.isErr(r)) {
			return GsonUtil.toString(ReceiveMsg.createNew(r.getCode(), r.getDesc()));
		}
		return GsonUtil.toString(ReceiveMsg.createNew(GsonUtil.toString(r.getData())));
	}
	
	public static <T> String packPage(Return<TFPageInfo<T>> r) {
		if (Return.isErr(r)) {
			return GsonUtil.toString(ReceiveMsg.createNew(r.getCode(), r.getDesc()));
		}
		return GsonUtil.toString(ReceiveMsg.createNew(GsonUtil.toString(r.getData())));
	}

	
	// ret 为通用返回对象

	public static <T> Return<T> ret() {
		return Return.createNew();
	}
	
	public static <T> Return<T> ret(T t) {
		return Return.createNew(t);
	}
	
	public static <T> Return<T> ret(EnumSessionError error) {
		return Return.createNew(error.getCode(), error.getDesc());
	}

	public static <T> Return<T> ret(int code, String desc) {
		return Return.createNew(code, desc);
	}
	public static <T> Return<T> ret(int code, String desc,T t) {
		return Return.createNew(code, desc,t);
	}
}

