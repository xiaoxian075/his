package com.his.common.net;

import com.his.common.session.SessionBo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseAccept {
	private SessionBo session;
	private String clientIp;
	private NetBo net;
	private String uri;
	
	public boolean check() {
		return true;
	}
}
