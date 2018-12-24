package com.his.clinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.clinic.accept.EntryClinicAccept;
import com.his.clinic.mq.MqSendAsyn;
import com.his.clinic.vo.ClinicVo;
import com.his.common.define.EnumSessionError;
import com.his.common.net.Constant;
import com.his.common.net.user.RedisUser;
import com.his.common.session.SessionBo;
import com.his.common.session.user.UserBo;
import com.his.common.session.user.clinic.ClinicBo;
import com.his.common.session.user.clinic.ClinicSession;
import com.tf.sdk.pojo.Return;

@Service
public class ClinicService {
	
	@Autowired
	private RedisUser redisUser;
	
	@Autowired
	private MqSendAsyn mqSendAsyn;

	public Return<ClinicVo> entry(EntryClinicAccept accept) {
		UserBo userBo = accept.getUser();
		if (userBo == null) {
			return Constant.ret(EnumSessionError.NO_MESSAGE_ERROR);
		}
		SessionBo session = accept.getSession();
		if (session == null) {
			return Constant.ret(EnumSessionError.NO_MESSAGE_ERROR);
		}
		
		// 设置诊所信息至SESSION中
		ClinicBo clinicBo = new ClinicBo(accept.getClinicId(), "测试诊所");
		ClinicSession.setClinic(session, clinicBo);
		
//		// 添加个人中心的权限到Session中
//		Map<String, ResourceBo> mapResource = authorityService.getCenterUri(user.getUserId());
//		if (mapResource == null) {
//			return Constant.ret(EnumSessionError.GET_POWER_ERROR);
//		}
//		if (!UserSession.setResource(session, mapResource)) {
//			return Constant.ret(EnumSessionError.SAVE_POWER_ERROR);
//		}
		
		// 保存Session到缓存中
		if (!redisUser.setSession(session)) {
			return Constant.ret(EnumSessionError.SAVE_REDIS_ERROR);
		}
		
		mqSendAsyn.sendLog(
				accept.getUri(),
				accept.getClientIp(),
				userBo.getUserId(),
				userBo.getUserName(),
				clinicBo.getClinicId(),
				clinicBo.getClinicName(),
				"进入诊所"
				);
		
		ClinicVo vo = new ClinicVo(clinicBo.getClinicId(), clinicBo.getClinicName());
		
		return Constant.ret(vo);
	}

}
