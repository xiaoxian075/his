package com.his.asyn.config;
//package com.his.asyn;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import com.his.asyn.mq.chit.JuHeChit;
//
///**
// * 服务开启后会执行一次，用于全局变量初始化
// * @author chenjx
// * @Version 1.0.0
// * @time   2018年5月11日
// */
//@Component
//@Order(value=101)
//public class CommandLine implements CommandLineRunner {
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//	
////	// 创蓝短信
////	@Value("${third.sms.cl.url}")
////    private String clUrl;
////	@Value("${third.sms.cl.account}")
////    private String clAccount;
////	@Value("${third.sms.cl.password}")
////    private String clPassword;
//
//	// 聚合短信
//	@Value("${third.sms.juhe.url}")
//	private String juheUrl;
//	@Value("${third.sms.juhe.AppKey}")
//	private String juheAppKey;
////	
////	@Resource
////	private SearchService searchService;
//
//	@Override
//	public void run(String... arg0) throws Exception {
//
////		// 初始化创蓝短信
////		if (StringUtils.isBlank(clUrl) || StringUtils.isBlank(clAccount) || StringUtils.isBlank(clPassword)) {
////			logger.error("init ChuangLang chit error");
////			return;
////		}
//		//MqReceiveChit.initCl(clUrl, clAccount, clPassword);
//
//		// 初始化聚合短信
//		if (StringUtils.isBlank(juheUrl) || StringUtils.isBlank(juheAppKey)) {
//			logger.error("init JuHe chit error");
//			return;
//		}
//		JuHeChit.init(juheUrl, juheAppKey);
//
//		//PywbMgr.init(searchService);
//	}
//}
