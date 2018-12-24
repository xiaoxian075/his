package com.his.common.session.user;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserBo implements Serializable {
	private static final long serialVersionUID = 588544669021098405L;

	private long userId;
	private String userName;
	private String identifiedId;
	private String identifiedName;
	private String phone;
	private String weixin;
	private String photo;
	private String desc;
}
