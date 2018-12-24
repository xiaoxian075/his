package com.his.asyn.mq;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.his.asyn.third.JuHeChit;
import com.his.common.define.EnumSessionError;
import com.his.common.mq.chit.ChitDefine;
import com.his.common.mq.chit.ChitPojo;
import com.his.common.net.Constant;
import com.tf.sdk.pojo.Return;
import com.tf.sdk.serializable.GsonUtil;

public class ChitReceiver {
	private final static Logger logger = LoggerFactory.getLogger(ChitReceiver.class);
	
	private JuHeChit juHeChit;
	
	public ChitReceiver(JuHeChit juHeChit) {
		this.juHeChit = juHeChit;
	}
	
	public Return<Object> dealMessage(String data) {
		ChitPojo chitPojo = GsonUtil.toJson(data, ChitPojo.class);
		if (chitPojo == null) {
			return Constant.ret(EnumSessionError.UNPACK_JSON_ERROR);
		}
		
		int type = chitPojo.getType();
		switch (type) {
		case ChitDefine.CLINIC_IC_ACCOUNT_REGISTER:
		case ChitDefine.CLINIC_IC_ACCOUNT_FORGET_PASSWORD:
		case ChitDefine.CLINIC_IC_ACCOUNT_WXBIND:
		case ChitDefine.CLINIC_IC_CREATE_CLINIC:
		case ChitDefine.CLINIC_IC_TRANSFER_CLINIC_OWNER:
		case ChitDefine.CLINIC_IC_TRANSFER_CLINIC_RECEIVER:
		case ChitDefine.ADMIN_IC_FORGET_PASSWORD:
			sendIdentifyingCode(chitPojo.getPhones()[0], chitPojo.getArgs()[0]);
			break;
		case ChitDefine.CLINIC_NO_CLINIC_VERIFY:
			sendClinicVerify(chitPojo.getPhones()[0], chitPojo.getArgs()[0], chitPojo.getArgs()[1]);
			break;
		case ChitDefine.CLINIC_NO_NOINVITE_PERSONNEL:
			sendInvitePresonnel(chitPojo.getPhones()[0], chitPojo.getArgs()[0], chitPojo.getArgs()[1]);
			break;
		default:
			return Constant.ret(EnumSessionError.TYPE_NOT_FIND_ERROR);
		}
		return Constant.ret();
	}
	
	
	
	
	private static final String HIS_URL= "https://his.retow.com";
	private static final String CODE_TIME= "5";
	
	/**
	 * 	【锐拓云诊所】您的验证码是#code#，有效期#m#分钟
	 */
	private final static String IDENTIFYING_CODE = "121240";
	private void sendIdentifyingCode(String phone, String code) {
	      Map<String,String> params = new HashMap<>(2);
	      params.put("#code#", code);
	      params.put("#m#", CODE_TIME);
	      if (!juHeChit.sendMsg(phone, IDENTIFYING_CODE, params)) {
	          logger.error("sendRetowYun fail, "
	        		  + "temp_id=" + INVITE_PRESONNEL_ID
	        		  + ",phone=" + phone
	        		  + ",code=" + code
	        		  + ",m=" + CODE_TIME);
	          return;
	      }
          logger.warn("sendRetowYun succ, "
        		  + "temp_id=" + INVITE_PRESONNEL_ID
        		  + ",phone=" + phone
        		  + ",code=" + code
        		  + ",m=" + CODE_TIME);
	}
	
	/**
	 * 	【锐拓云诊所】您创建的诊所“#clinicname#”，审核状态为：#state#，您可在(个人中心)中查看审核详情
	 */
	private final static String CLINIC_VERIFY_ID = "122714";
	private void sendClinicVerify(String phone, String clinicName, String state) {
	      Map<String,String> params = new HashMap<>(2);
	      params.put("#clinicname#", clinicName);
	      params.put("#state#", state);
	      if (!juHeChit.sendMsg(phone, CLINIC_VERIFY_ID, params)) {
	          logger.error("sendClinicVerify fail, "
	        		  + "temp_id=" + INVITE_PRESONNEL_ID
	        		  + ",phone=" + phone
	        		  + ",state=" + state);	
	          return;
	      }
          logger.warn("sendClinicVerify succ, "
        		  + "temp_id=" + INVITE_PRESONNEL_ID
        		  + ",phone=" + phone
        		  + ",state=" + state);	
	}
	
	/**
	 * 	【锐拓云诊所】#username#邀请您加入“#clinicname#"诊所，详情点击#hisurl#
	 */
	private final static String INVITE_PRESONNEL_ID = "122709";
	private void sendInvitePresonnel(String phone, String userName, String clinicName) {
		
	      Map<String,String> params = new HashMap<>(3);
	      params.put("#username#", userName);
	      params.put("#clinicname#", clinicName);
	      params.put("#hisurl#", HIS_URL);
	      if (!juHeChit.sendMsg(phone, INVITE_PRESONNEL_ID, params)) {
	          logger.error("sendInvitePresonnel fail, "
	        		  + "temp_id=" + INVITE_PRESONNEL_ID
	        		  + ",phone=" + phone 
	        		  + ",username=" + userName
	        		  + ",clinicname=" + clinicName
	        		  + ",hisurl=" + HIS_URL);
	          return;
	      }
          logger.warn("sendInvitePresonnel succ, "
        		  + "temp_id=" + INVITE_PRESONNEL_ID
        		  + ",phone=" + phone 
        		  + ",username=" + userName
        		  + ",clinicname=" + clinicName
        		  + ",hisurl=" + HIS_URL);
	}

}
