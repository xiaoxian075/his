package com.his.common.net.user;

import org.springframework.data.redis.core.RedisTemplate;

import com.his.common.session.SessionBo;
import com.tf.sdk.redis.MyRedis;


public class RedisUser {
	
	private MyRedis redis;
	
	public RedisUser(RedisTemplate<Object, Object> redis) {
		this.redis = new MyRedis(redis);
	}
	
	// session
	private final static String SESSION_TOKEN = "session_token_";
	private final static String SESSION_ACCOUNDID = "session_accountid_";
	private final static long SESSION_TIMEOUT = 120;	// 300*120
	
	public SessionBo getSession(long accountId) {
		String primary = (String)redis.get(SESSION_ACCOUNDID + accountId);
		if (primary == null) {
			return null;
		}
		return (SessionBo)redis.get(primary);
	}
	public SessionBo getSession(String token) {
		return (SessionBo)redis.get(SESSION_TOKEN + token);
	}
	public SessionBo getAndFlushSession(String token) {
		String primary = SESSION_TOKEN + token;
		SessionBo session = (SessionBo)redis.get(primary);
		if (session != null) {
			redis.expire(primary, SESSION_TIMEOUT);
			redis.expire(SESSION_ACCOUNDID + session.getAccountId(), SESSION_TIMEOUT);
		}
		return session;
	}

	public boolean setSession(SessionBo session) {
		if (!redis.set(SESSION_ACCOUNDID + session.getAccountId(), SESSION_TOKEN + session.getToken(), SESSION_TIMEOUT)) {
			return false;
		}
		return redis.set(SESSION_TOKEN + session.getToken(), session, SESSION_TIMEOUT);
	}
	public void removeSession(String token) {
		SessionBo session = (SessionBo)redis.get(SESSION_TOKEN + token);
		if (session != null) {
			redis.del(SESSION_TOKEN + token);
			redis.del(SESSION_ACCOUNDID + session.getAccountId());
		}
	}
	public void removeSession(long accountId) {
		String primary = (String)redis.get(SESSION_ACCOUNDID + accountId);
		if (primary != null) {
			redis.del(SESSION_ACCOUNDID + accountId);
			redis.del(primary);
		}
	}

}


//private final static String ERROR_LOGIN_COUNT = "ERROR_LOGIN_COUNT_";
//	public Integer getAndPlusErrorLoginCount(String key) {
//	String primary = ERROR_LOGIN_COUNT + key;
//	Integer count = (Integer)super.get(primary);
//	if (count != null) {
//		count++;
//	}else {
//		count = 1;
//	}
//	super.set(primary,count);
//	super.expire(primary, 3600*2);
//	return count;
//}
//
//public Integer getErrorLoginCount(String key) {
//	String primary = ERROR_LOGIN_COUNT + key;
//	Integer count = (Integer)super.get(primary);
//	return count;
//}
//
//public void removeErrorLoginCount(String key) {
//	String primary = ERROR_LOGIN_COUNT + key;
//	del(primary);
//}
//
//public Long getErrorLoginExpire(String key) {
//	String primary = ERROR_LOGIN_COUNT + key;
//	return super.getExpire(primary);
//}
