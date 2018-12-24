package com.his.yun.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.his.common.mq.log.LogPojo;
import com.his.yun.mq.MqSendAsyn;
import com.tf.sdk.util.DateUtil;

@RestController
@RequestMapping(value = "/test")
public class TestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private MqSendAsyn mqSendAsyn;
	
	
	/**
	 * 获取诊所导航(根据自身角色去获取)
	 * @return
	 */
	@RequestMapping(value = "/sendChit")
    public String sendChit(HttpServletRequest request) {
		try {
			String phone = request.getParameter("phone");
			String identifyingCode = request.getParameter("code");
			
			if (!mqSendAsyn.sendClinicIcAccountRegister(phone, identifyingCode)) {
				return "FAIL";
			}
			
			return "SUCC";
		} catch (Exception e) {
			logger.error(request.getServletPath(),e);
			return "FAIL";
		}
	}

	/**
	 * 发送业务日志
	 * @return
	 */
	@RequestMapping(value = "/sendLog")
    public String sendLog(HttpServletRequest request) {
		try {
			LogPojo log = new LogPojo(
					34987L,
					"孙权",
					12233L,
					"先峰",
					1003,
					"登入",
					1,
					"个人中心",
					"192.168.10.77",
					DateUtil.currentTime(),
					"首次登入云诊所");
			if (!mqSendAsyn.sendLog(log)) {
				return "FAIL";
			}
			
			return "SUCC";
		} catch (Exception e) {
			logger.error(request.getServletPath(),e);
			return "FAIL";
		}
	}
}
