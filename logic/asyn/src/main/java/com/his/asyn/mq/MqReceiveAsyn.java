package com.his.asyn.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.common.define.EnumSessionError;
import com.his.common.mq.MqDefine;
import com.his.common.mq.MqReceiver;
import com.his.common.net.Constant;
import com.tf.sdk.pojo.Return;

public class MqReceiveAsyn extends MqReceiver {
	private final static Logger logger = LoggerFactory.getLogger(MqReceiveAsyn.class);
	
	private ChitReceiver chit;
	private ElasticReceiver elastic;
	private LogReceiver log;
	
	public MqReceiveAsyn(ChitReceiver chit, ElasticReceiver elastic, LogReceiver log) {
		this.chit = chit;
		this.elastic = elastic;
		this.log = log;
	}

	@Override
	protected void dealMessage(int type, String content) {
		
		Return<Object> ret = null;
		try {
			switch (type) {
			case MqDefine.COMMON_CHIT:
				ret = chit.dealMessage(content);
				break;
			case MqDefine.COMMON_ELASTIC:
				ret = elastic.dealMessage(content);
				break;
			case MqDefine.COMMON_ELASTIC_LIST:
				ret = elastic.dealMessageList(content);
				break;
			case MqDefine.COMMON_LOG:
				ret = log.dealMessage(content);
				break;
			default:
				ret = Constant.ret(EnumSessionError.TYPE_NOT_FIND_ERROR);
				break;
			}
			
			if (Return.isErr(ret)) {
				logger.error("dealMessage deal fail. code=" + ret.getCode() + 
						",desc=" + ret.getDesc() + 
						",type=" + type + 
						",content=" + content);
			}
		} catch (Exception e) {
			logger.error("dealMessage deal fail, type=" + type + ",content=" + content);
			logger.error("dealMessage deal fail", e);
		}
	}
	
}
