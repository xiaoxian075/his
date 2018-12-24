package com.his.clinic.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.his.clinic.accept.LoginAccept;
import com.his.clinic.net.Parse;
import com.his.clinic.service.UserService;
import com.his.clinic.vo.LoginVo;
import com.his.common.define.CommonDefine;
import com.his.common.define.EnumSessionError;
import com.his.common.net.Constant;
import com.tf.sdk.pojo.Return;


@RestController
@RequestMapping(value = "user")
public class UserController {
	private final static Logger logger = LoggerFactory.getLogger(UserController.class);	

	@Autowired
	private Parse parse;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 登入
	 */
	@RequestMapping(value = "login", produces = CommonDefine.JSON_PRODUCES, method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
		try {
			Return<LoginAccept> retParam = parse.parse(request, LoginAccept.class);
			if (Return.isErr(retParam)) {
				return Constant.pack(retParam);
			}
			LoginAccept accept = retParam.getData();
			
			Return<LoginVo> ret = userService.login(accept);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			return Constant.pack(ret.getData());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(EnumSessionError.EXCEPTION_ERROR);
		}
	}
}
