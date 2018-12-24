package com.his.clinic.net;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Type;

import com.his.common.net.BaseAccept;
import com.his.common.net.Net;
import com.his.common.net.user.RedisUser;
import com.his.common.net.user.UserAccept;
import com.his.common.net.user.UserNet;
import com.his.common.net.user.clinic.ClinicAccept;
import com.his.common.net.user.clinic.ClinicNet;
import com.tf.sdk.pojo.Return;


public class Parse {

	private RedisUser redis;
	
	public Parse(RedisUser redis) {
		this.redis = redis;
	}
	
	public RedisUser getRedis() {
		return redis;
	}
	
	public <T extends BaseAccept> Return<T> parse(HttpServletRequest request, Type type) {
		return Net.parseParam(request, type);
	}


	public <T extends UserAccept> Return<T> parseLogin(HttpServletRequest request, Type type) {
		return UserNet.parseLogin(request, type, redis);
	}
	
	public <T extends UserAccept> Return<T> parseAuth(HttpServletRequest request, Type type) {
		return UserNet.parseAuth(request, type, redis);
	}
	
	public <T extends ClinicAccept> Return<T> parseClinicEnter(HttpServletRequest request, Type type) {
		return ClinicNet.parseClinicEnter(request, type, redis);
	}
	
	public <T extends ClinicAccept> Return<T> parseClinicAuth(HttpServletRequest request, Type type) {
		return ClinicNet.parseClinicAuth(request, type, redis);
	}
	
	
}
