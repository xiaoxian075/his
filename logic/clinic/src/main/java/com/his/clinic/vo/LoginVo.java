package com.his.clinic.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginVo implements Serializable {
	private static final long serialVersionUID = 6444392371779332389L;
	
	private String token;
	private long userId;
	private String userName;
	private String identifiedId;
	private String identifiedName;
	private String phone;
	private String weixin;
	private String photo;
	private String desc;
}
