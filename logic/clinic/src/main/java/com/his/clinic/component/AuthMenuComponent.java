package com.his.clinic.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.reflect.TypeToken;
import com.his.clinic.repository.mapper.AuthMapper;
import com.his.clinic.repository.mapper.MenuLogMapper;
import com.his.clinic.repository.mapper.MenuMapper;
import com.his.clinic.repository.model.Auth;
import com.his.clinic.repository.model.Menu;
import com.his.clinic.repository.model.MenuLog;
import com.his.common.menu.AuthPojo;
import com.his.common.menu.MenuLogPojo;
import com.his.common.menu.MenuPojo;
import com.tf.sdk.serializable.GsonUtil;

@Component
public class AuthMenuComponent {

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private AuthMapper authMapper;
	
	@Autowired
	private MenuLogMapper menuLogMapper;
	


	public List<MenuPojo> selectMenuAll() {
		List<Menu> listMenu = menuMapper.selectAll();
		if (listMenu == null) {
			return null;
		}
		
		List<MenuPojo> listMenuPojo = new ArrayList<MenuPojo>();
		for (Menu menu : listMenu) {
			listMenuPojo.add(new MenuPojo(
					menu.getId(),
					menu.getParentId(),
					menu.getTitle(),
					menu.getIcon(),
					menu.getRoute(),
					menu.getSort()
					));
		}
		
		return listMenuPojo;
	}


	public Map<Long, AuthPojo> selectAuthAll() {
		List<Auth> listAuth = authMapper.selectAll();
		if (listAuth == null) {
			return null;
		}
		 
		
		Map<Long, AuthPojo> mapAuth = new ConcurrentHashMap<Long, AuthPojo>();
		for (Auth auth : listAuth) {
			List<String> listUri = GsonUtil.toJson(auth.getUris(), new TypeToken<List<String>>() {}.getType());
			if (listUri == null) {
				return null;
			}
			mapAuth.put(auth.getId(), 
				new AuthPojo(auth.getId(), auth.getMenuId(), listUri));
		}
		
		return mapAuth;
	}
	
	public Map<String, MenuLogPojo> selectMenuLogAll() {
		List<MenuLog> listMenuLog = menuLogMapper.selectAll();
		if (listMenuLog == null) {
			return null;
		}
		 
		
		Map<String, MenuLogPojo> mapMenuLog = new ConcurrentHashMap<String, MenuLogPojo>();
		for (MenuLog menuLog : listMenuLog) {
			mapMenuLog.put(menuLog.getUri(), 
				new MenuLogPojo(menuLog.getMenuId(),
					menuLog.getMenuName(),
					menuLog.getModelId(),
					menuLog.getModelName()));
		}
		
		return mapMenuLog;
	}
}
