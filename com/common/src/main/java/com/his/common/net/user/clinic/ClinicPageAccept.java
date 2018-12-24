package com.his.common.net.user.clinic;

import lombok.Setter;

@Setter
public class ClinicPageAccept extends ClinicAccept {
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
			size = 100;
		}
		return size;
	}
}
