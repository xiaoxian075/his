package com.his.asyn.mq;

import com.his.asyn.log.LogService;
import com.his.common.define.EnumSessionError;
import com.his.common.mq.log.LogPojo;
import com.his.common.net.Constant;
import com.tf.sdk.pojo.Return;
import com.tf.sdk.serializable.GsonUtil;

public class LogReceiver {
	private LogService logService;
	
	public LogReceiver(LogService logService) {
		this.logService = logService;
	}
	
	
	public Return<Object> dealMessage(String content) {
		LogPojo logPojo = GsonUtil.toJson(content, LogPojo.class);
		if (logPojo == null) {
			return Constant.ret(EnumSessionError.UNPACK_JSON_ERROR);
		}
		return logService.dealClinicLog(logPojo);
	}

}
