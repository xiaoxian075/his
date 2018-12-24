package com.his.asyn.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.his.asyn.repository.mapper.ClinicLogMapper;
import com.his.asyn.repository.model.ClinicLog;
import com.his.common.define.EnumSessionError;
import com.his.common.mq.log.LogPojo;
import com.his.common.net.Constant;
import com.tf.sdk.pojo.Return;
import com.tf.sdk.serializable.GsonUtil;

@Component
public class LogService {
	private final static Logger logger = LoggerFactory.getLogger(LogService.class);
	
	@Autowired
	private ClinicLogMapper clinicLogMapper;

	public Return<Object> dealClinicLog(LogPojo log) {
		
		ClinicLog record = new ClinicLog(
				0L,
				log.getUserId(),
				log.getUserName(),
				log.getClinicId(),
				log.getClinicName(),
				log.getMenuId(),
				log.getMenuName(),
				log.getModelId(),
				log.getModelName(),
				log.getClientIp(),
				log.getTimestamp(),
				log.getContent()
				);

		if (1 != clinicLogMapper.insert(record)) {
			logger.error("LogService dealClinicLog insert fail, record:" + GsonUtil.toString(record));
			return Constant.ret(EnumSessionError.DB_INSERT_ERROR);
		}
		return Constant.ret();
	}
}