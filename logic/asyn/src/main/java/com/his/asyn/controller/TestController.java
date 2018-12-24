package com.his.asyn.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.his.asyn.accept.ElasticAccept;
import com.his.asyn.accept.ElasticSearchAccept;
import com.his.asyn.elastic.ElasticService;
import com.his.asyn.elastic.PywuVo;
import com.his.asyn.net.Parse;
import com.his.common.define.CommonDefine;
import com.his.common.define.EnumSessionError;
import com.his.common.net.BaseAccept;
import com.his.common.net.Constant;
import com.his.common.net.NetBo;
import com.his.common.net.user.RedisUser;
import com.his.common.session.SessionBo;
import com.his.common.session.user.UserBo;
import com.his.common.session.user.UserSession;
import com.tf.sdk.pojo.Return;

@RestController
@RequestMapping(value = "test")
public class TestController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());	
	
	@Autowired
	private Parse parse;
	
	@Autowired
	private ElasticService elastic;
	
	/**
	 * 登入
	 */
	@RequestMapping(value = "login", produces = CommonDefine.JSON_PRODUCES, method = RequestMethod.POST)
    public String login(HttpServletRequest request) {
		try {
			Return<BaseAccept> retParam = parse.parse(request, BaseAccept.class);
			if (Return.isErr(retParam)) {
				return Constant.pack(retParam);
			}
			BaseAccept accept = retParam.getData();
			
			String clientIp = accept.getClientIp();
			NetBo net = accept.getNet();
			String token = net.getToken();
			
			UserBo user = new UserBo();
			user.setUserId(12345433);
			SessionBo session = UserSession.createSession(clientIp, token, user);
			
			RedisUser redis = parse.getRedis();
			if (!redis.setSession(session)) {
				return Constant.pack(EnumSessionError.SAVE_REDIS_ERROR);
			}
			
			
			return Constant.pack(session);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(EnumSessionError.EXCEPTION_ERROR);
		}
	}
	
	/**
	 * 查询
	 */
	@RequestMapping(value = "searchElastic", produces = CommonDefine.JSON_PRODUCES, method = RequestMethod.POST)
    public String searchElastic(HttpServletRequest request) {
		try {
			Return<ElasticSearchAccept> retParam = parse.parseLogin(request, ElasticSearchAccept.class);
			if (Return.isErr(retParam)) {
				return Constant.pack(retParam);
			}
			ElasticSearchAccept accept = retParam.getData();
			
			int page = accept.getPage();
			int size = accept.getSize();
			int type = accept.getType();
			String word = accept.getWord();
			long extra = accept.getExtra();
			
			Return<List<Long>> ret = elastic.allSearch(page, size, type, word, extra);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			return Constant.pack(ret);
		} catch (Exception e) {
			logger.error(request.getServletPath(), e);
			return Constant.pack(EnumSessionError.EXCEPTION_ERROR);
		}
	}

	/**
	 * 	添加引擎记录
	 */
	@RequestMapping(value = "addElastic", produces = CommonDefine.JSON_PRODUCES, method = RequestMethod.POST)
    public String addElastic(HttpServletRequest request) {
		try {
			Return<ElasticAccept> retParam = parse.parseLogin(request, ElasticAccept.class);
			if (Return.isErr(retParam)) {
				return Constant.pack(retParam);
			}
			ElasticAccept accept = retParam.getData();
			long id = accept.getId();
			int type = accept.getType();
			String word = accept.getWord();
			long extra = accept.getExtra();
			
			Return<PywuVo> ret = elastic.add(id, type, word, extra);
			if (Return.isErr(ret)) {
				return Constant.pack(ret);
			}
			
			return Constant.pack(ret.getData());
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Constant.pack(EnumSessionError.EXCEPTION_ERROR);
		}
	}

}
