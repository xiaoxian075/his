package com.his.asyn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.his.asyn.third.PinYin;
import com.his.asyn.third.WuBi;

@Configuration
public class AsynConfig {

	@Bean
	public PinYin pinyin(@Value("${third.character.path.pinyin}")String fileName) {
		return new PinYin(fileName);
	}
	
	@Bean
	public WuBi wubi(@Value("${third.character.path.wubi}")String fileName) {
		return new WuBi(fileName);
	}
}
