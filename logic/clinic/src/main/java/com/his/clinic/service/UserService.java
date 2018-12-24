package com.his.clinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.his.clinic.accept.LoginAccept;
import com.his.clinic.mq.MqSendAsyn;
import com.his.clinic.repository.mapper.LoginMapper;
import com.his.clinic.repository.mapper.UserMapper;
import com.his.clinic.repository.model.Login;
import com.his.clinic.repository.model.User;
import com.his.clinic.vo.LoginVo;
import com.his.common.define.EnumSessionError;
import com.his.common.net.Constant;
import com.his.common.net.user.RedisUser;
import com.his.common.session.SessionBo;
import com.his.common.session.user.UserBo;
import com.his.common.session.user.UserSession;
import com.tf.sdk.pojo.Return;
import com.tf.sdk.util.Md5Util;
import com.tf.sdk.util.RandomUtil;

@Service
public class UserService {
	
	@Autowired
	private RedisUser redisUser;
	
	@Autowired
	private MqSendAsyn mqSendAsyn;
	
	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private UserMapper userMapper;

	public Return<LoginVo> login(LoginAccept accept) {
		String clientIp = accept.getClientIp();
		String account = accept.getAccount();
		String password = accept.getPassword();
		
		// 获取Login对象
		Login login = loginMapper.selectByAccount(account);
		if (login == null) {
			return Constant.ret(EnumSessionError.ACCOUNT_NOT_EXIST_ERROR);
		}
		
		// 对数据库中密码加验证码进行md5加密
		password = Md5Util.PasswordMd5(account, password);
		if (password == null) {
			return Constant.ret(EnumSessionError.NO_MESSAGE_ERROR);
		}

		if (!account.equals(login.getAccount()) || !password.equals(login.getPassword())) {
			return Constant.ret(EnumSessionError.ACCOUNT_OR_PWD_ERROR);
		}

		Return<LoginVo> ret = login(clientIp, login);
		if (Return.isErr(ret)) {
			return ret;
		}
		LoginVo vo = ret.getData();
		
		mqSendAsyn.sendLog(
				accept.getUri(),
				accept.getClientIp(),
				vo.getUserId(),
				vo.getUserName(),
				"登入"
				);
		return ret;
	}
	
	
	private Return<LoginVo> login(String clientIp, Login login) {

		long userId = login.getUserId()==null?0:login.getUserId();
		// 登录后将用户信息存到session中
		User user = userMapper.selectByUserId(userId);
		if (user == null) {
			return Constant.ret(EnumSessionError.ACCOUNT_INFO_ERROR);
		}
		UserBo userBo = new UserBo(
				user.getUserId(),
				user.getUserName(),
				user.getIdentifiedId(),
				user.getIdentifiedName(),
				user.getPhone(),
				user.getWeixin(),
				user.getPhoto(),
				user.getDesc());
		
		// 踢掉已登入
		SessionBo oldSession = redisUser.getSession(userId);
		if (oldSession != null) {
			redisUser.removeSession(userId);
		}
		
		// 将session信息保存到redis中
		String token = RandomUtil.generatorToken();
		if (token == null) {
			return Constant.ret(EnumSessionError.NO_MESSAGE_ERROR);
		}
		SessionBo session = UserSession.createSession(clientIp, token, userBo);
		if (session == null) {
			return Constant.ret(EnumSessionError.NO_MESSAGE_ERROR);
		}
		
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
		
        LoginVo vo = new LoginVo(
        		token, 
        		userBo.getUserId(),
        		userBo.getUserName(),
        		userBo.getIdentifiedId(),
        		userBo.getIdentifiedName(),
        		userBo.getPhone(),
        		userBo.getWeixin(),
        		userBo.getPhoto(),
        		userBo.getDesc());
        return Constant.ret(vo);
	}

}
