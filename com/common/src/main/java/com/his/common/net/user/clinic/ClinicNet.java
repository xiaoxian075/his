package com.his.common.net.user.clinic;

import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;

import com.his.common.define.EnumSessionError;
import com.his.common.net.user.RedisUser;
import com.his.common.net.user.UserNet;
import com.his.common.session.SessionBo;
import com.his.common.session.user.UserSession;
import com.his.common.session.user.clinic.ClinicBo;
import com.his.common.session.user.clinic.ClinicSession;
import com.tf.sdk.pojo.Return;

public class ClinicNet extends UserNet {

	/**
	 * 是否有进入诊所
	 * @param request
	 * @param c
	 * @return
	 */
	public static <T extends ClinicAccept> Return<T> parseClinicEnter(HttpServletRequest request, Type c, RedisUser redis) {
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
		
		ClinicBo clinic = ClinicSession.getClinic(session);
		if (!ClinicSession.checkClinic(clinic)) {
			return Return.createNew(EnumSessionError.NET_CLINIC_ENTER_ERROR.getCode(), EnumSessionError.NET_CLINIC_ENTER_ERROR.getDesc());
		}
		t.setClinic(clinic);
		
		return Return.createNew(t);
	}
	
	
	/**
	 * 诊所数据解析(参数、进入诊所、权限)
	 * @param request
	 * @param c
	 * @return
	 */
	public static <T extends ClinicAccept> Return<T> parseClinicAuth(HttpServletRequest request, Type c, RedisUser redis) {

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
		
		ClinicBo clinic = ClinicSession.getClinic(session);
		if (!ClinicSession.checkClinic(clinic)) {
			return Return.createNew(EnumSessionError.NET_CLINIC_ENTER_ERROR.getCode(), EnumSessionError.NET_CLINIC_ENTER_ERROR.getDesc());
		}
		t.setClinic(clinic);
		
		// 权限验证
		if (!ClinicSession.checkClinicAuth(session, t.getUri())) {
			return Return.createNew(EnumSessionError.NET_CLINIC_AUTH_ERROR.getCode(), EnumSessionError.NET_CLINIC_AUTH_ERROR.getDesc());
		}	
		
		return Return.createNew(t);		
	}
}
