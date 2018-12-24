package com.his.common.session.user.clinic;

import java.util.List;

import com.his.common.net.MenuBo;
import com.his.common.session.SessionBo;
import com.his.common.session.user.UserSession;

import lombok.Getter;

@Getter
public class ClinicSession extends UserSession {
	
	private final static String SESSION_CLINIC_INFO = "session_clinic_info";
	private final static String SESSION_CLINIC_MENU = "session_clinic_menu";
	private final static String SESSION_CLINIC_AUTH = "session_clinic_auth";
	
	public static ClinicBo getClinic(SessionBo session) {
		return (ClinicBo)session.oGetObj(SESSION_CLINIC_INFO);
	}
	
	@SuppressWarnings("unchecked")
	public static List<MenuBo> getClinicMenu(SessionBo session) {
		return (List<MenuBo>)session.oGetObj(SESSION_CLINIC_MENU);
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getClinicAuth(SessionBo session) {
		return (List<String>)session.oGetObj(SESSION_CLINIC_AUTH);
	}
	
	public static ClinicSession createNew(SessionBo session) {
		return new ClinicSession(session);
	}
	
	
	private ClinicBo clinic;
	private List<MenuBo> listClinicMenu;
	private List<String> listClinicAuth;
	
	protected ClinicSession(SessionBo session) {
		super(session);
		this.clinic = getClinic(session);
		this.listClinicMenu = getClinicMenu(session);
		this.listClinicAuth = getClinicAuth(session);
	}
	

	
	
	

	
	


	public boolean checkClinic() {
		return checkClinic(clinic);
	}
	
	/**
	 * 权限校验
	 * @param uri
	 * @return
	 */
	public boolean checkClinicAuth(String uri) {
		return checkClinicAuth(listClinicAuth, uri);
	}

	public static boolean checkClinic(ClinicBo clinicBo) {
		if (clinicBo == null) {
			return false;
		}
		return true;
	}
	
	


	public static boolean checkClinicAuth(SessionBo session, String uri) {
		List<String> listClinicAuth = getClinicAuth(session);
		return checkClinicAuth(listClinicAuth, uri);
	}
	/**
	 * 权限校验
	 * @param uri
	 * @return
	 */
	public static boolean checkClinicAuth(List<String> listClinicAuth, String uri) {
		if (listClinicAuth == null || listClinicAuth.size() == 0) {
			return false;
		}
		
		if (!listClinicAuth.contains(uri)) {
			return false;
		}
		
		return true;
	}

	
	public static void setClinic(SessionBo session, ClinicBo clinicBo) {
		session.oSetObj(SESSION_CLINIC_INFO, clinicBo);
	}
}
