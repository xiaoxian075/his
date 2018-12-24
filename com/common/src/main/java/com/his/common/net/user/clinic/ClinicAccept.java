package com.his.common.net.user.clinic;

import com.his.common.net.user.UserAccept;
import com.his.common.session.user.clinic.ClinicBo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClinicAccept extends UserAccept {
	private ClinicBo clinic;
}
