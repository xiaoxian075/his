package com.tf.sdk.util;

import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * 说明
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月11日
 */
public class RandomUtil {

	public final static String generatorToken() {
		 return UUID.randomUUID().toString().replace("-", "");
	}
	
	public final static String getUuid() {
		 return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * 生成19随机单号 纯数字
	 */
	public static String generatorOrderNo() {
		String orderNo = "" ;
		String trandNo = String.valueOf((Math.random() * 9 + 1) * 1000000);
		long curTime = System.currentTimeMillis();
		String sdf = new SimpleDateFormat("yyyyMMddHHMMSS").format(curTime);
		orderNo = trandNo.toString().substring(0, 4);
		orderNo = orderNo + sdf;
		return orderNo;
	}
	
	/**
	 * 随机产生6位数字
	 * @return
	 */
	public static String generatorPhoneCode() {
		int code = (int)((Math.random()*9+1)*100000);
		return String.valueOf(code);
//		return "888888";
	}
	
	public static int randInt() {
		return (int)((Math.random()*9+1)*100000);
	}
}
