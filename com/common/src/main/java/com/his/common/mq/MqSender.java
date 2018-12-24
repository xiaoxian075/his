package com.his.common.mq;

import org.apache.commons.lang3.StringUtils;
import com.tf.sdk.serializable.GsonUtil;

public class MqSender {

	public String sendData(int type, String data) {
		if (type <= 0 || StringUtils.isEmpty(data)) {
			return null;
		}
		
		MqCommonPojo commonPojo = new MqCommonPojo(type, data);
		String content = GsonUtil.toString(commonPojo);
		if (content == null) {
			return null;
		}
		
		return content;
	}
}
