package com.his.common.net;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Type;

import com.his.common.define.EnumSessionError;
import com.his.common.net.user.RedisUser;
import com.his.common.session.SessionBo;
import com.tf.sdk.pojo.Return;
import com.tf.sdk.serializable.GsonUtil;
import com.tf.sdk.util.NetUtil;

public class Net {

	/**
	 * 请求数据解析(验请参数)
	 * @param request
	 * @param c
	 * @return
	 */
	public static <T extends BaseAccept> Return<T> parseSession(HttpServletRequest request, Type c, RedisUser redis) {

		Return<T> ret = parse(request, c);
		if (Return.isErr(ret)) {
			return ret;
        }
		T t = ret.getData();
		
		// 验证参数
		if (!t.check()) {
			return Return.createNew(EnumSessionError.NET_PARSE_PARAM_ERROR.getCode(), EnumSessionError.NET_PARSE_PARAM_ERROR.getDesc());
		}
		
		String token = t.getNet().getToken();
		SessionBo session = redis.getAndFlushSession(token);
		if (session == null) {
			return Return.createNew(EnumSessionError.NET_ACCOUNT_LOGIN_ERROR.getCode(), EnumSessionError.NET_ACCOUNT_LOGIN_ERROR.getDesc());
		}
		t.setSession(session);
		
		return Return.createNew(t);
	}
	
	/**
	 * 请求数据解析(验请参数)
	 * @param request
	 * @param c
	 * @return
	 */
	public static <T extends BaseAccept> Return<T> parseParam(HttpServletRequest request, Type c) {

		Return<T> ret = parse(request, c);
		if (Return.isErr(ret)) {
			return ret;
        }
		T t = ret.getData();
		
		// 验证参数
		if (!t.check()) {
			return Return.createNew(EnumSessionError.NET_PARSE_PARAM_ERROR.getCode(), EnumSessionError.NET_PARSE_PARAM_ERROR.getDesc());
		}
		
		return Return.createNew(t);
	}
	
	
	/**
	 * 请求数据解析
	 * @param request
	 * @param c
	 * @return
	 */
	private static <T extends BaseAccept> Return<T> parse(HttpServletRequest request, Type c) {
		T t = null;
		try {
			String clientIp = NetUtil.getIpAddress(request);
			if (StringUtils.isBlank(clientIp)) {
				return Return.createNew(EnumSessionError.NET_CLINT_IP_ERROR.getCode(), EnumSessionError.NET_CLINT_IP_ERROR.getDesc());
			}
			
			String uri = request.getServletPath();
			if (StringUtils.isBlank(uri)) {
				return Return.createNew(EnumSessionError.NET_URI_ERROR.getCode(), EnumSessionError.NET_URI_ERROR.getDesc());
			}

			String netData = NetUtil.parse(request);
	    	if (StringUtils.isBlank(netData) || netData.length() < 2) {
	    		return Return.createNew(EnumSessionError.NET_DATA_LENGTH_ERROR.getCode(), EnumSessionError.NET_DATA_LENGTH_ERROR.getDesc());
			}
	    	
	    	NetBo netBo = GsonUtil.toJson(netData, NetBo.class);
	    	if (netBo == null) {
	    		return Return.createNew(EnumSessionError.NET_DATA_JSON_ERROR.getCode(), EnumSessionError.NET_DATA_JSON_ERROR.getDesc());
	    	}
	    	
			int encrypt = netBo.getEncrypt();
			if (encrypt < 0) {
				;
			}
			long timestamp = netBo.getTimestamp();
			if (timestamp < 0) {
				;
			}
			String token = netBo.getToken();
			if (StringUtils.isBlank(token)) {
				;
			}
			String sign = netBo.getSign();
			if (StringUtils.isBlank(sign)) {
				;
			}
			String str = netBo.getData();
			if (StringUtils.isBlank(str) || str.length() < 2) {
				return Return.createNew(EnumSessionError.NET_LOGIC_DATA_LENGTH_ERROR.getCode(), EnumSessionError.NET_LOGIC_DATA_LENGTH_ERROR.getDesc());
			}
	    	t = GsonUtil.toJson(str, c);
	    	if (t == null) {
	    		return Return.createNew(EnumSessionError.NET_LOGIC_DATA_JSON_ERROR.getCode(), EnumSessionError.NET_LOGIC_DATA_JSON_ERROR.getDesc());
	    	}
	    	
	    	t.setClientIp(clientIp);
	    	t.setNet(netBo);
	    	t.setUri(uri);
		} catch (Exception e) {
			return Return.createNew(EnumSessionError.NET_PARSE_ERROR.getCode(), EnumSessionError.NET_PARSE_ERROR.getDesc());
		}
		
		return Return.createNew(t);
	}
}
