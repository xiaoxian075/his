package com.his.common.net.user;

import com.his.common.net.BaseAccept;
import com.his.common.session.user.UserBo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserAccept extends BaseAccept {
	private UserBo user;
}
