package com.his.common.net.user;

import lombok.Setter;

@Setter
public class UserPageAccept extends UserAccept {
	private int page;
	private int size;
	
	public int getPage() {
		if (page < 0) {
			page = 0;
		}
		return page;
	}
	
	public int getSize() {
		if (size <= 0) {
			size = 20;
		}
		return size;
	}
}
