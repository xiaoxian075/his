package com.his.clinic.mq;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.his.common.menu.AuthMenu;
import com.his.common.menu.MenuLogPojo;
import com.his.common.mq.chit.ChitDefine;
import com.his.common.mq.chit.ChitSend;
import com.his.common.mq.elastic.ElasticDefine;
import com.his.common.mq.elastic.ElasticNode;
import com.his.common.mq.elastic.ElasticSend;
import com.his.common.mq.log.LogPojo;
import com.his.common.mq.log.LogSend;
import com.tf.sdk.mq.AmqpAbstractProducer;
import com.tf.sdk.util.DateUtil;

@Component
public class MqSendAsyn extends AmqpAbstractProducer {
	
	@Autowired
	private ChitSend chit;
	
	@Autowired
	private ElasticSend elastic;
	
	@Autowired
	private LogSend logSend;

	@Autowired
	public MqSendAsyn(RabbitTemplate rabbitTemplate, @Value("${spring.rabbitmq.send.asyn}") String mqKey) {
		super(rabbitTemplate, mqKey);
	}
	
	//public 
	

	/**
	 * 诊所--创建诊所
	 * @param phone
	 * @param identifyingCode
	 * @return
	 */
	public boolean sendClinicIcCreateClinic(String phone, String identifyingCode) {
		String content = chit.sendChit(ChitDefine.CLINIC_IC_CREATE_CLINIC, phone, identifyingCode);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	
	/**
	 * 诊所--转让诊所（拥有者）
	 * @param phone
	 * @param identifyingCode
	 * @return
	 */
	public boolean sendClinicIcTransferClinicOwner(String phone, String identifyingCode) {
		String content = chit.sendChit(ChitDefine.CLINIC_IC_TRANSFER_CLINIC_OWNER, phone, identifyingCode);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);		
	}
	
	/**
	 * 诊所--转让诊所（接收者）
	 * @param phone
	 * @param identifyingCode
	 * @return
	 */
	public boolean sendClinicIcTransferClinicReceiver(String phone, String identifyingCode) {
		String content = chit.sendChit(ChitDefine.CLINIC_IC_TRANSFER_CLINIC_RECEIVER, phone, identifyingCode);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);			
	}
	
	/**
	 * 诊所--添加用户
	 * @param phone
	 * @param userName
	 * @param clinicName
	 */
	public boolean sendClinicNoInvitePresonnel(String phone, String userName, String clinicName) {
		String content = chit.sendChit(ChitDefine.CLINIC_NO_CLINIC_VERIFY, phone, userName, clinicName);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);			
	}
	
	/**
	 * 诊所--审核短信推送
	 * @param phone
	 * @param clinicName
	 * @param state
	 * @return
	 */
	public boolean sendClinicVerify(String phone, String clinicName, String state) {
		String content = chit.sendChit(ChitDefine.CLINIC_NO_NOINVITE_PERSONNEL, phone, clinicName, state);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);			
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 药品 drug
	public boolean addDrug(long clinicId, long bid, String word) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_ADD, ElasticDefine.PYWB_DATA_DRUG, bid, word, clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean updateDrug(long clinicId, long bid, String word) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_UPDATE, ElasticDefine.PYWB_DATA_DRUG, bid, word, clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean deleteDrug(long id, long clinicId) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_DELETE, ElasticDefine.PYWB_DATA_DRUG, id, "", clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean addDrugList(List<ElasticNode> listElastic) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_ADD, ElasticDefine.PYWB_DATA_DRUG, listElastic);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	
	
	// 项目 item
	public boolean addItem(long clinicId, long bid, String word) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_ADD, ElasticDefine.PYWB_DATA_ITEM, bid, word, clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean updateItem(long clinicId, long bid, String word) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_UPDATE, ElasticDefine.PYWB_DATA_ITEM, bid, word, clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean deleteItem(long id, long clinicId) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_DELETE, ElasticDefine.PYWB_DATA_ITEM, id, "", clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean addItemList(List<ElasticNode> listElastic) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_ADD, ElasticDefine.PYWB_DATA_ITEM, listElastic);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	
	
	// 诊断 diagnose
	public boolean addDiagnose(long clinicId, long bid, String word) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_ADD, ElasticDefine.PYWB_DATA_DIAGNOSE, bid, word, clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean updateDiagnose(long clinicId, long bid, String word) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_UPDATE, ElasticDefine.PYWB_DATA_DIAGNOSE, bid, word, clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean deleteDiagnose(long id, long clinicId) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_DELETE, ElasticDefine.PYWB_DATA_DIAGNOSE, id, "", clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean addDiagnoseList(List<ElasticNode> listElastic) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_ADD, ElasticDefine.PYWB_DATA_DIAGNOSE, listElastic);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	
	
	
	// 患者 patient
	public boolean addPatient(long clinicId, long bid, String word) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_ADD, ElasticDefine.PYWB_DATA_PATIENT, bid, word, clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean updatePatient(long clinicId, long bid, String word) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_UPDATE, ElasticDefine.PYWB_DATA_PATIENT, bid, word, clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	public boolean deletePatient(long id, long clinicId) {
		String content = elastic.sendElastic(ElasticDefine.PYWB_OPERATOR_TYPE_DELETE, ElasticDefine.PYWB_DATA_PATIENT, id, "", clinicId);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}

	
	
	public boolean sendLog(String uri, String clientIp, long userId, String userName, String content) {
		
		return sendLog(uri, clientIp, userId, userName, 0L, "", content);
	}
	
	public boolean sendLog(String uri, String clientIp, long userId, String userName, long clinicId, String clinicName, String content) {
		
		MenuLogPojo menu = AuthMenu.getMenuLogByUri(uri);
		if (menu == null) {
			menu = AuthMenu.createMenuLog();
		}
		LogPojo log = new LogPojo(
				userId,
				userName,
				clinicId,
				clinicName,
				menu.getMenuId(),
				menu.getMenuName(),
				menu.getModelId(),
				menu.getModelName(),
				clientIp,
				DateUtil.currentTime(),
				content
				);
		
		String data = logSend.sendLog(log);
		if (data == null) {
			return false;
		}
		
		return super.sendMsg(data);
	}
}
