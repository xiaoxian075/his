package com.his.common.mq.log;

import com.his.common.mq.MqDefine;
import com.his.common.mq.MqSender;
import com.tf.sdk.serializable.GsonUtil;

public class LogSend extends MqSender {
	
	/**
	 * 推送业务日志到日志服务
	 * @param log 日志内容
	 * @return
	 */
	public String sendLog(LogPojo log) {

		if (log == null) {
			return null;
		}

		String data = GsonUtil.toString(log);
		if (data == null) {
			return null;
		}

		return super.sendData(MqDefine.COMMON_LOG, data);

	}
}
