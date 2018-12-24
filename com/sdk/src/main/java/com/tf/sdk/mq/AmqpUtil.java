package com.tf.sdk.mq;

import org.springframework.util.StringUtils;

public class AmqpUtil {
	
	private final static String QUEUE_SUFFIX = "-queue";
	private final static String EXCHANGE_SUFFIX = "-exchange";
	private final static String ROUTINGKEY_SUFFIX = "-routingkey";
	
	public static String getQueue(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		
		return key + QUEUE_SUFFIX;
	}
	
	public static String getExchange(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		
		return key + EXCHANGE_SUFFIX;
	}
	
	public static String getRoutingkey(String key) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		
		return key + ROUTINGKEY_SUFFIX;
	}

}
