package com.his.asyn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class,JpaRepositoriesAutoConfiguration.class})
@ServletComponentScan
@MapperScan("com.his.asyn.repository.mapper")
public class AsynApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsynApplication.class, args);
	}
}

////exclude 为忽略database
//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class,JpaRepositoriesAutoConfiguration.class})
////配置druid必须加的注解，如果不加，访问页面打不开，filter和servlet、listener之类的需要单独进行注册才能使用，spring boot里面提供了该注解起到注册作用
//@ServletComponentScan
////mybatis注解，扫描mybatis接口类
//@MapperScan("com.his.log.repository.mapper")
//////开启事务
////@EnableTransactionManagement(proxyTargetClass = true)
////增加扫描路径（用于引用其它的工程时也可以用spring进行统一管理）
//@ComponentScan(basePackages = {"com.his.log", "com.his.sdk", "com.his.session.account", "com.his.session.admin", "com.his.sdk.loghub"})


