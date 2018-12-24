package com.his.clinic.accept;

import com.his.common.net.user.UserAccept;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryClinicAccept extends UserAccept {
	private long clinicId;
	
	public boolean check() {
		if (!super.check()) {
			return false;
		}
		
		if (clinicId <= 0) {
			return false;
		}
		
		return true;
	}
}
