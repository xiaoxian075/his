package com.his.common.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * Session
 * @author chenjx
 * @Version 3.0.0
 * @time   2018年12月21日
 */
@Getter
@Setter
public class SessionBo implements Serializable {
	private static final long serialVersionUID = 3368900935291655244L;
	
	private String clientIp;
	private String token;
	private long accountId;
	private Map<String, Serializable> mapObj;

	public SessionBo() {
		super();
		this.clientIp = "";
		this.token = "";
		this.accountId = 0;
		this.mapObj = new HashMap<String, Serializable>();
	}

	public SessionBo(String clientIp, String token, long accountId) {
		super();
		this.clientIp = clientIp;
		this.token = token;
		this.accountId = accountId;
		this.mapObj = new HashMap<String, Serializable>();
	}
	
	public Serializable oGetObj(String key) {
		return mapObj.get(key);
	}
	public void oSetObj(String key, Serializable value) {
		mapObj.put(key, value);
	}
	public void oRemoveObj(String key) {
		mapObj.remove(key);
	}
}

