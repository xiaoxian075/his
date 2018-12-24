package com.his.common.mq;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tf.sdk.mq.IMqReceive;
import com.tf.sdk.serializable.GsonUtil;

public abstract class MqReceiver implements IMqReceive {
	private final static Logger logger = LoggerFactory.getLogger(MqReceiver.class);
	
	
	@Override
	public void onMessage(String data) {
		if (StringUtils.isBlank(data)) {
			logger.error("MqReceiver onMessage data is null, data:" + data);
			return;
		}
		
		MqCommonPojo commonPojo = GsonUtil.toJson(data, MqCommonPojo.class);
		if (commonPojo == null) {
			logger.error("MqReceiver onMessage gson data fail, data:" + data);
			return;
		}
		
		int type = commonPojo.getType();
		String content = commonPojo.getData();
		if (type <= 0 || StringUtils.isBlank(content)) {
			logger.error("MqReceiver onMessage MqCommonPojo fail, type:" + type + ",content:" + content);
			return;
		}

		dealMessage(type, content);
		
	}


	protected abstract void dealMessage(int type, String content);

}
