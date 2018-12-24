package com.his.clinic.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.his.clinic.accept.EntryClinicAccept;
import com.his.clinic.net.Parse;
import com.his.clinic.service.ClinicService;
import com.his.clinic.vo.ClinicVo;
import com.his.common.define.CommonDefine;
import com.his.common.define.EnumSessionError;
import com.his.common.net.Constant;
import com.tf.sdk.pojo.Return;

@RestController
@RequestMapping(value = "clinic")
public class ClinicController {
	private final static Logger logger = LoggerFactory.getLogger(ClinicController.class);	

	@Autowired
	private Parse parse;
	
	@Autowired
	private ClinicService clinicService;
	
	/**
	 * 登入
	 */
	@RequestMapping(value = "enter", produces = CommonDefine.JSON_PRODUCES, method = RequestMethod.POST)
    public String enter(HttpServletRequest request) {
		try {
			Return<EntryClinicAccept> retParam = parse.parseLogin(request, EntryClinicAccept.class);
			if (Return.isErr(retParam)) {
				return Constant.pack(retParam);
			}
			EntryClinicAccept accept = retParam.getData();

			Return<ClinicVo> ret = clinicService.entry(accept);
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
