package com.his.common.mq.elastic;

import java.util.List;

import com.his.common.mq.MqDefine;
import com.his.common.mq.MqSender;
import com.tf.sdk.serializable.GsonUtil;
import com.tf.sdk.util.StringUtil;

public class ElasticSend extends MqSender {

	/**
	 * 推送到搜索引擎
	 * @param operatorType	1增加、2修改、3删除
	 * @param dataType	1药品、2项目
	 * @param id
	 * @param word
	 * @param clinicId
	 * @return
	 */
	public String sendElastic(int operatorType, int dataType, long id, String word, long clinicId) {

		if (
				!ElasticDefine.checkPywbOperator(operatorType) ||
				!ElasticDefine.checkPywbData(dataType) ||
				id <= 0 ||
				!StringUtil.checkNotNullAndLength(word, 2, 64) ||
				clinicId < 0) {
			return null;
		}
		
			ElasticPojo elasticPojo = new ElasticPojo(operatorType, dataType, id, clinicId, word);
			String data = GsonUtil.toString(elasticPojo);
			if (data == null) {
				return null;
			}
			
			return super.sendData(MqDefine.COMMON_ELASTIC, data);

	}
	
	/**
	 * 推送到搜索引擎
	 * @param operatorType	1增加、2修改、3删除
	 * @param dataType	1药品、2项目
	 * @param id
	 * @param word
	 * @param clinicId
	 * @return
	 */
	public String sendElastic(int operatorType, int dataType, List<ElasticNode> listData) {

		if (
				!ElasticDefine.checkPywbOperator(operatorType) ||
				!ElasticDefine.checkPywbData(dataType) ||
				listData == null || listData.size() == 0) {
			return null;
		}
		
		ElasticListPojo elasticPojo = new ElasticListPojo(operatorType, dataType, listData);
		String data = GsonUtil.toString(elasticPojo);
		if (data == null) {
			return null;
		}
		
		return super.sendData(MqDefine.COMMON_ELASTIC_LIST, data);
	}
}
