package com.his.clinic.accept;

import org.apache.commons.lang3.StringUtils;

import com.his.common.net.BaseAccept;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginAccept extends BaseAccept {
	private String account;
	private String password;
	
	public boolean check() {
		if (!super.check()) {
			return false;
		}
		
		if (StringUtils.isBlank(account) || account.length() > 32 ||
				StringUtils.isBlank(password) || password.length() > 255) {
			return false;
		}
		
		return true;
	}	
}
