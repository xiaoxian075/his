package com.his.clinic.config;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.his.clinic.component.AuthMenuComponent;
import com.his.common.menu.AuthMenu;
import com.his.common.menu.AuthPojo;
import com.his.common.menu.MenuLogPojo;
import com.his.common.menu.MenuPojo;

/**
 * 服务开启后会执行一次，用于全局变量初始化
 * @author chenjx
 * @Version 1.0.0
 * @time   2018年5月11日
 */
@Component
@Order(value=101)
public class CommandLine implements CommandLineRunner {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthMenuComponent authMenuComponent;

	@Override
	public void run(String... arg0) throws Exception {
		List<MenuPojo> listMenu = authMenuComponent.selectMenuAll();
		if (listMenu == null) {
			logger.error("init menu fail");
			return;
		}
		
		Map<Long, AuthPojo> mapAuth = authMenuComponent.selectAuthAll();
		if (mapAuth == null) {
			logger.error("init auth log fail");
			return;
		}
		
		Map<String, MenuLogPojo> mapMenuLog = authMenuComponent.selectMenuLogAll();
		if (mapMenuLog == null) {
			logger.error("init menu log fail");
			return;
		}
		
		AuthMenu.init(listMenu, mapAuth, mapMenuLog);

	}
}
