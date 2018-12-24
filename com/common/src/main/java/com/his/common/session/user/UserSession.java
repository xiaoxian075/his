package com.his.common.session.user;

import java.io.Serializable;
import java.util.List;

import com.his.common.net.MenuBo;
import com.his.common.session.Session;
import com.his.common.session.SessionBo;

import lombok.Getter;

@Getter
public class UserSession extends Session {

	
	private final static String SESSION_USER_INFO = "session_user_info";
	private final static String SESSION_USER_MENU = "session_user_menu";
	private final static String SESSION_USER_AUTH = "session_user_auth";
	
	public static UserBo getUser(SessionBo session) {
		return (UserBo)session.oGetObj(SESSION_USER_INFO);
	}
	
	@SuppressWarnings("unchecked")
	public static List<MenuBo> getUserMenu(SessionBo session) {
		return (List<MenuBo>)session.oGetObj(SESSION_USER_MENU);
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getUserAuth(SessionBo session) {
		return (List<String>)session.oGetObj(SESSION_USER_AUTH);
	}
	
	public static UserSession createNew(SessionBo session) {
		return new UserSession(session);
	}
	
	
	private UserBo user;
	private List<MenuBo> listUserMenu;
	private List<String> listUserAuth;
	
	protected UserSession(SessionBo session) {
		super(session);
		this.user = getUser(session);
		this.listUserMenu = getUserMenu(session);
		this.listUserAuth = getUserAuth(session);
	}
	

	
	
	

	
	

	
	/**
	 * 登入校验
	 * @return
	 */
	public boolean checkLogin() {
		if (!checkLogin(token, user)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 权限校验
	 * @param uri
	 * @return
	 */
	public boolean checkUserAuth(String uri) {
		return checkUserAuth(listUserAuth, uri);
	}
	
	/**
	 * 登入校验
	 * @return
	 */
	public static boolean checkLogin(SessionBo session) {
		String token = session.getToken();
		UserBo user = UserSession.getUser(session);
		return checkLogin(token, user);
	}
	
	/**
	 * 登入校验
	 * @return
	 */
	public static boolean checkLogin(String token, UserBo user) {
		if(!checkToken(token)) {
			return false;
		}
		
		if (user == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 权限校验
	 * @param uri
	 * @return
	 */
	public static boolean checkUserAuth(SessionBo session, String uri) {
		List<String> listUserAuth = getUserAuth(session);
		return checkUserAuth(listUserAuth, uri);
	}
	
	/**
	 * 权限校验
	 * @param uri
	 * @return
	 */
	public static boolean checkUserAuth(List<String> listAuth, String uri) {
		if (listAuth == null || listAuth.size() == 0) {
			return false;
		}
		
		if (!listAuth.contains(uri)) {
			return false;
		}
		
		return true;
	}

	public static SessionBo createSession(String clientIp, String token, UserBo user) {
		SessionBo session = new SessionBo(clientIp, token, user.getUserId());
		session.oSetObj(SESSION_USER_INFO, user);
		return session;
	}
	
	public static boolean setUserAuth(SessionBo session, List<String> listAuth) {
		if (session == null || listAuth == null) {
			return false;
		}
		session.oSetObj(SESSION_USER_AUTH, (Serializable)listAuth);
		return true;
	}
	
}
