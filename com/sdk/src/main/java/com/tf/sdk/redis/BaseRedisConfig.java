package com.tf.sdk.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Duration;

import redis.clients.jedis.JedisPoolConfig;

public class BaseRedisConfig {
	
    protected RedisTemplate<Object, Object> redisTemplate(
    		int redisDb,
    		String redisHost,
    		String redisAuth,
    		int redisPort,
    		int redisTimeout,
    		int maxActive,
    		int maxIdle,
    		int minIdle,
    		int maxWait) {
    	RedisConnectionFactory factory = connectionFactory(
        		redisDb,
        		redisHost,
        		redisAuth,
        		redisPort,
        		redisTimeout,
        		maxActive,
        		maxIdle,
        		minIdle,
        		maxWait);
    	RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();
    	template.setConnectionFactory(factory);
    	
//    	RedisSerializer<?> stringSerializer = new StringRedisSerializer();
//    	template.setKeySerializer(stringSerializer);
//    	template.setValueSerializer(stringSerializer);
//    	template.setHashKeySerializer(stringSerializer);
//    	template.setHashValueSerializer(stringSerializer);
    	
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//        template.afterPropertiesSet();
        return template;
    }
    
    
    
    private RedisConnectionFactory connectionFactory(
    		int redisDb,
    		String redisHost,
    		String redisAuth,
    		int redisPort,
    		int redisTimeout,
    		int maxActive,
    		int maxIdle,
    		int minIdle,
    		int maxWait) {
	    JedisPoolConfig poolConfig = new JedisPoolConfig();
	    poolConfig.setMaxTotal(maxActive);
	    poolConfig.setMaxIdle(maxIdle);
	    poolConfig.setMaxWaitMillis(maxWait);
	    poolConfig.setMinIdle(minIdle);
	    poolConfig.setTestOnBorrow(true);
	    poolConfig.setTestOnReturn(false);
	    poolConfig.setTestWhileIdle(true);
	    JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
	            .usePooling().poolConfig(poolConfig).and().readTimeout(Duration.ofMillis(redisTimeout)).build();

	    // 单点redis
	    RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
	    // 哨兵redis
	    // RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
	    // 集群redis
	    // RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
	    redisConfig.setHostName(redisHost);
	    redisConfig.setPassword(RedisPassword.of(redisAuth));
	    redisConfig.setPort(redisPort);
	    redisConfig.setDatabase(redisDb);

	    return new JedisConnectionFactory(redisConfig,clientConfig);
	}
}
