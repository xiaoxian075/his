package com.his.yun.mq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.his.common.mq.chit.ChitDefine;
import com.his.common.mq.chit.ChitSend;
import com.his.common.mq.log.LogPojo;
import com.his.common.mq.log.LogSend;
import com.tf.sdk.mq.AmqpAbstractProducer;

@Component
public class MqSendAsyn extends AmqpAbstractProducer {
	
	@Autowired
	private ChitSend chit;
	
	@Autowired
	private LogSend log;

	@Autowired
	public MqSendAsyn(RabbitTemplate rabbitTemplate, @Value("${spring.rabbitmq.send.asyn}") String mqKey) {
		super(rabbitTemplate, mqKey);
	}

	/**
	 * 诊所--账号创建
	 * @param phone
	 * @param identifyingCode
	 * @return
	 */
	public boolean sendClinicIcAccountRegister(String phone, String identifyingCode) {
		String content = chit.sendChit(ChitDefine.CLINIC_IC_ACCOUNT_REGISTER, phone, identifyingCode);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	
	/**
	 * 诊所--账号找回密码
	 * @param phone
	 * @param identifyingCode
	 * @return
	 */
	public boolean sendClinicIcAccountForgetPassword(String phone, String identifyingCode) {
		String content = chit.sendChit(ChitDefine.CLINIC_IC_ACCOUNT_FORGET_PASSWORD, phone, identifyingCode);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}

	/**
	 * 诊所--账号微信绑定
	 * @param phone
	 * @param identifyingCode
	 * @return
	 */
	public boolean sendClinicIcAccountWxbind(String phone, String identifyingCode) {
		String content = chit.sendChit(ChitDefine.CLINIC_IC_ACCOUNT_WXBIND, phone, identifyingCode);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
	
	/**
	 * 发送业务日志
	 * @param logPojo	日志内容
	 * @return
	 */
	public boolean sendLog(LogPojo logPojo) {
		String content = log.sendLog(logPojo);
		if (content == null) {
			return false;
		}
		return super.sendMsg(content);
	}
}
	
//
//	/**
//	 * 诊所--创建诊所
//	 * @param phone
//	 * @param identifyingCode
//	 * @return
//	 */
//	public String sendClinicIcCreateClinic(String phone, String identifyingCode) {
//		return sendChit(ChitDefine.CLINIC_IC_CREATE_CLINIC, phone, identifyingCode);
//	}
//	
//	/**
//	 * 诊所--转让诊所（拥有者）
//	 * @param phone
//	 * @param identifyingCode
//	 * @return
//	 */
//	public String sendClinicIcTransferClinicOwner(String phone, String identifyingCode) {
//		return sendChit(ChitDefine.CLINIC_IC_TRANSFER_CLINIC_OWNER, phone, identifyingCode);
//	}
//	
//	/**
//	 * 诊所--转让诊所（接收者）
//	 * @param phone
//	 * @param identifyingCode
//	 * @return
//	 */
//	public String sendClinicIcTransferClinicReceiver(String phone, String identifyingCode) {
//		return sendChit(ChitDefine.CLINIC_IC_TRANSFER_CLINIC_RECEIVER, phone, identifyingCode);
//	}
//	
//	/**
//	 * 诊所--添加用户
//	 * @param phone
//	 * @param userName
//	 * @param clinicName
//	 */
//	public String sendClinicNoInvitePresonnel(String phone, String userName, String clinicName) {
//		return sendChit(ChitDefine.CLINIC_NO_CLINIC_VERIFY, phone, userName, clinicName);
//	}
//	
//	/**
//	 * 诊所--审核短信推送
//	 * @param phone
//	 * @param clinicName
//	 * @param state
//	 * @return
//	 */
//	public String sendClinicVerify(String phone, String clinicName, String state) {
//		return sendChit(ChitDefine.CLINIC_NO_NOINVITE_PERSONNEL, phone, clinicName, state);
//	}
//
//	
//	/**
//	 * 后台--账号忘记密码
//	 * @param phone
//	 * @param identifyingCode
//	 * @return
//	 */
//	public String sendAdminIcForgetPassword(String phone, String identifyingCode) {
//		return sendChit(ChitDefine.ADMIN_IC_FORGET_PASSWORD, phone, identifyingCode);
//	}	
