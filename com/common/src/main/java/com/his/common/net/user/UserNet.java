package com.his.common.net.user;

import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Type;

import com.his.common.define.EnumSessionError;
import com.his.common.net.Net;
import com.his.common.session.SessionBo;
import com.his.common.session.user.UserSession;
import com.tf.sdk.pojo.Return;

public class UserNet extends Net {

	/**
	 * 网络数据解析(参数、登入)
	 * @param request
	 * @param c
	 * @return
	 */
	public static <T extends UserAccept> Return<T> parseLogin(HttpServletRequest request, Type c, RedisUser redis) {
		// 解析数据
		Return<T> ret = parseSession(request, c, redis);
		if (Return.isErr(ret)) {
            return Return.createNew(ret.getCode(), ret.getDesc());
        }
		T t = ret.getData();
		
//		if (!param.getClientIP().equals(session.getClientIP())) {
//			logger.error("param clientIP=" + param.getClientIP() + ",sessionIP=" + session.getClientIP());
//			return Return.createNew(SessionEnumError.IP_ERROR.getCode(), SessionEnumError.IP_ERROR.getDesc());
//		}
		
		SessionBo session = t.getSession();
		if (!UserSession.checkLogin(session)) {
			return Return.createNew(EnumSessionError.NET_ACCOUNT_LOGIN_ERROR.getCode(), EnumSessionError.NET_ACCOUNT_LOGIN_ERROR.getDesc());
		}
		t.setUser(UserSession.getUser(session));
		
		return Return.createNew(t);
	}
	
	/**
	 * 网络数据解析(参数、登入、权限)
	 * @param request
	 * @param c
	 * @return
	 */
	public static <T extends UserAccept> Return<T> parseAuth(HttpServletRequest request, Type c, RedisUser redis) {
		// 解析数据
		Return<T> ret = parseSession(request, c, redis);
		if (Return.isErr(ret)) {
            return Return.createNew(ret.getCode(), ret.getDesc());
        }
		T t = ret.getData();
		
//				if (!param.getClientIP().equals(session.getClientIP())) {
//					logger.error("param clientIP=" + param.getClientIP() + ",sessionIP=" + session.getClientIP());
//					return Return.createNew(SessionEnumError.IP_ERROR.getCode(), SessionEnumError.IP_ERROR.getDesc());
//				}
		
		SessionBo session = t.getSession();
		if (!UserSession.checkLogin(session)) {
			return Return.createNew(EnumSessionError.NET_ACCOUNT_LOGIN_ERROR.getCode(), EnumSessionError.NET_ACCOUNT_LOGIN_ERROR.getDesc());
		}
		t.setUser(UserSession.getUser(session));
		
		// 权限验证
		if (!UserSession.checkUserAuth(session, t.getUri())) {
			return Return.createNew(EnumSessionError.NET_ACCOUNT_AUTH_ERROR.getCode(), EnumSessionError.NET_ACCOUNT_AUTH_ERROR.getDesc());
		}
		
		return Return.createNew(t);		
	}
}
