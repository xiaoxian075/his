package com.his.asyn.mq;

import java.util.List;

import com.his.asyn.elastic.ElasticService;
import com.his.asyn.elastic.PywuVo;
import com.his.common.define.EnumSessionError;
import com.his.common.mq.elastic.ElasticDefine;
import com.his.common.mq.elastic.ElasticListPojo;
import com.his.common.mq.elastic.ElasticNode;
import com.his.common.mq.elastic.ElasticPojo;
import com.his.common.net.Constant;
import com.tf.sdk.pojo.Return;
import com.tf.sdk.serializable.GsonUtil;

public class ElasticReceiver {
	
	private ElasticService elasticService;
	
	public ElasticReceiver(ElasticService elasticService) {
		this.elasticService = elasticService;
	}
	
	public Return<Object> dealMessage(String content) {
		ElasticPojo elasticPojo = GsonUtil.toJson(content, ElasticPojo.class);
		if (elasticPojo == null) {
			return Constant.ret(EnumSessionError.UNPACK_JSON_ERROR);
		}
		
		int operatorType = elasticPojo.getOperatorType();
		int dataType = elasticPojo.getDataType();
		long id = elasticPojo.getId();
		long clinicId = elasticPojo.getClinicId();
		String word = elasticPojo.getWord();
		

		Return<PywuVo> ret = null;
		switch (operatorType) {
		case ElasticDefine.PYWB_OPERATOR_TYPE_ADD:
			ret = elasticService.add(id, dataType, word, clinicId);
			break;
		case ElasticDefine.PYWB_OPERATOR_TYPE_UPDATE:
			ret = elasticService.update(id, dataType, word, clinicId);
			break;
		case ElasticDefine.PYWB_OPERATOR_TYPE_DELETE:
			ret = elasticService.delete(id, dataType);
			break;
		default:
			return Constant.ret(EnumSessionError.TYPE_NOT_FIND_ERROR);			
		}
		
		return Constant.ret(ret.getCode(), ret.getDesc());
		
	}

	public Return<Object> dealMessageList(String content) {
		ElasticListPojo elasticListPojo = GsonUtil.toJson(content, ElasticListPojo.class);
		if (elasticListPojo == null) {
			return Constant.ret(EnumSessionError.UNPACK_JSON_ERROR);
		}
		
		int operatorType = elasticListPojo.getOperatorType();
		int dataType = elasticListPojo.getDataType();
		List<ElasticNode> listData = elasticListPojo.getListData();
		
		Return<List<PywuVo>> ret = null;
		switch (operatorType) {
		case ElasticDefine.PYWB_OPERATOR_TYPE_ADD:
			ret = elasticService.addList(dataType, listData);
			break;
		default:
			return Constant.ret(EnumSessionError.TYPE_NOT_FIND_ERROR);				
		}
		
		return Constant.ret(ret.getCode(), ret.getDesc());	
	}

}
