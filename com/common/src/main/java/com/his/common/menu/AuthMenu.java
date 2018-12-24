package com.his.common.menu;

import java.util.List;
import java.util.Map;

public class AuthMenu {
	
	/**
	 * 菜单
	 */
	private static List<MenuPojo> listMenu;
	
	/**
	 * 	权限
	 */
	private static Map<Long, AuthPojo> mapAuth;
	
	/**
	 * 	菜单日志
	 */
	private static Map<String, MenuLogPojo> mapMenuLog;
	
	public static void init(List<MenuPojo> _listMenu, Map<Long, AuthPojo> _mapAuth, Map<String, MenuLogPojo> _mapMenuLog) {
		listMenu = _listMenu;
		mapAuth = _mapAuth;
		mapMenuLog = _mapMenuLog;
	}

	public static MenuLogPojo getMenuLogByUri(String uri) {
		return mapMenuLog.get(uri);
	}

	public static MenuLogPojo createMenuLog() {
		return new MenuLogPojo(0, "", 0, "");
	}
}
