package com.tf.sdk.serializable;

/**
 * Json对象转换工具
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月9日
 */
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtil {

	//Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
	private static Gson gson = new GsonBuilder().serializeNulls().create();
	
	/**
	 * 转换成JSON字符串
	 * @param t
	 * @return
	 */
	public static <T> String toString(T t) {
		//Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<T>()).create();
//		if (t == null) {
//			return "";
//		}
		return gson.toJson(t);
	}
	
	/**
	 * 转换成对象
	 * 泛型：new TypeToken<List<Integer>>() {}.getType()
	 * @param data
	 * @param c
	 * @return
	 */
	public static <T> T toJson(String data, Type c) {
		//Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory<T>()).create();
		try{
			return gson.fromJson(data, c);
		} catch (Exception e) {
			return null;
		}
	}
}
