package com.his.common.session;

/**
 * Session操作类
 * @author chenjx
 * @Version 3.0.0
 * @time   2018年12月21日
 */
public class Session {
	
	public static Session createNew(SessionBo session) {
		return new Session(session);
	}

	private SessionBo session;
	
	protected String token;
	protected long accountId;

	protected Session(SessionBo session) {
		this.session = session;
		this.token = session.getToken();
		this.accountId = session.getAccountId();
	}
	
	public SessionBo getSession() {
		return session;
	}
	
	public String getToken() {
		return token;
	}
	
	public long getAccountId() {
		return accountId;
	}
	
	/**
	 * 登入校验
	 * @return
	 */
	public boolean checkToken() {
		return checkToken(token);
	}
	
	
	public static boolean checkToken(String token) {
		if (token == null) {
			return false;
		}
		return true;
	}
}
