package com.his.asyn.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.his.asyn.net.Parse;
import com.his.common.net.user.RedisUser;
import com.tf.sdk.redis.BaseRedisConfig;
 
 
@Configuration
public class RedisUserConfig extends BaseRedisConfig {
	
	@Value("${spring.redis.user.database}")
	private int redisDb;
	
	@Value("${spring.redis.user.host}")
	private String redisHost;

	@Value("${spring.redis.user.password}")
	private String redisAuth;

	@Value("${spring.redis.user.port}")
	private int redisPort;

	@Value("${spring.redis.timeout}")
	private int redisTimeout;

	@Value("${spring.redis.jedis.pool.max-active}")
	private int maxActive;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int maxIdle;

	@Value("${spring.redis.jedis.pool.min-idle}")
	private int minIdle;

	@Value("${spring.redis.jedis.pool.max-wait}")
	private int maxWait;
	

	@Bean(name = "user")
    public RedisTemplate<Object, Object> redisTemplate() {
    	return super.redisTemplate(redisDb, redisHost, redisAuth, redisPort, redisTimeout, maxActive, maxIdle, minIdle, maxWait);
    }
	
	@Bean
	public Parse parse() {
		RedisTemplate<Object, Object> redisTemplate = this.redisTemplate();
		RedisUser redis = new RedisUser(redisTemplate);
		return new Parse(redis);
	}
}
