package com.his.asyn.accept;

import com.his.common.net.user.UserAccept;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ElasticAccept extends UserAccept {
	private long id;
	private int type;
	private String word;
	private long extra;
	
	public boolean check() {
		return true;
	}
}
