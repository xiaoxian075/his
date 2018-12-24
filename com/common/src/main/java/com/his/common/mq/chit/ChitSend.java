package com.his.common.mq.chit;

import com.his.common.mq.MqDefine;
import com.his.common.mq.MqSender;
import com.tf.sdk.serializable.GsonUtil;

public class ChitSend extends MqSender {
	
//	/**
//	 * 诊所--账号创建
//	 * @param phone
//	 * @param identifyingCode
//	 * @return
//	 */
//	public String sendClinicIcAccountRegister(String phone, String identifyingCode) {
//		return sendChit(ChitDefine.CLINIC_IC_ACCOUNT_REGISTER, phone, identifyingCode);
//	}
//	
//	/**
//	 * 诊所--账号找回密码
//	 * @param phone
//	 * @param identifyingCode
//	 * @return
//	 */
//	public String sendClinicIcAccountForgetPassword(String phone, String identifyingCode) {
//		return sendChit(ChitDefine.CLINIC_IC_ACCOUNT_FORGET_PASSWORD, phone, identifyingCode);
//	}
//
//	/**
//	 * 诊所--账号微信绑定
//	 * @param phone
//	 * @param identifyingCode
//	 * @return
//	 */
//	public String sendClinicIcAccountWxbind(String phone, String identifyingCode) {
//		return sendChit(ChitDefine.CLINIC_IC_ACCOUNT_WXBIND, phone, identifyingCode);
//	}
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
	
	/**
	 * 单手机号短信推送
	 * @param type		类型（用于匹配短信模板）
	 * @param phone		手机号
	 * @param args		具体内容
	 * @return
	 */
	public String sendChit(int type, String phone, String... args) {
		String[] phones = {phone};
		return sendChit(type, phones, args);
	}
	
	/**
	 * 多手机号短信推送
	 * @param type		类型（用于匹配短信模板）
	 * @param phone		手机号
	 * @param args		具体内容
	 * @return
	 */
	public String sendChit(int type, String[] phones, String... args) {
		if (type <= 0 || phones == null || phones.length < 1 || args == null) {
			return null;
		}
		
		ChitPojo chitPojo = new ChitPojo(type, phones, args);
		String chitData = GsonUtil.toString(chitPojo);
		if (chitData == null) {
			return null;
		}
			
		return sendData(MqDefine.COMMON_CHIT, chitData);
	}
	
//	private boolean sendData(int type, String data) {
//		if (type <= 0 || StringUtils.isEmpty(data)) {
//			return false;
//		}
//		
//		MqCommonPojo commonPojo = new MqCommonPojo(type, data);
//		data = GsonUtil.toString(commonPojo);
//		if (data == null) {
//			return false;
//		}
//		
//		return super.sendMsg(data);
//	}
}
