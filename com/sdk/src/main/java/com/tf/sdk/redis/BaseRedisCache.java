package com.tf.sdk.redis;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.tf.sdk.serializable.GsonUtil;

import redis.clients.jedis.JedisPoolConfig;

public class BaseRedisCache extends CachingConfigurerSupport {
	
	/**
	 * 定义缓存数据 key 生成策略的bean (包名+类名+方法名+所有参数 )
	 * @return
	 */
	protected KeyGenerator _keyGenerator(){
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append("$").append(method.getName());
				for (Object obj : params) {
					String item = ",";
					if(obj==null){
						item += "";
					}else {
						item += obj.toString();
					}
					sb.append(item);
				}
				return GsonUtil.toString(sb.toString());
			}
		};
	}

	protected CacheManager cacheManager(
			int redisDb,
    		String redisHost,
    		String redisAuth,
    		int redisPort,
    		int redisTimeout,
    		int maxActive,
    		int maxIdle,
    		int minIdle,
    		int maxWait,
    		long cacheTimeout) {
		return cacheManager(
			redisDb,
    		redisHost,
    		redisAuth,
    		redisPort,
    		redisTimeout,
    		maxActive,
    		maxIdle,
    		minIdle,
    		maxWait,
    		cacheTimeout,
    		null);
	}
	
    protected StringRedisTemplate stringRedisTemplate(
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
    	StringRedisTemplate template = new StringRedisTemplate();
    	template.setConnectionFactory(factory);
        return template;
    }
			
	protected CacheManager cacheManager(
    		int redisDb,
    		String redisHost,
    		String redisAuth,
    		int redisPort,
    		int redisTimeout,
    		int maxActive,
    		int maxIdle,
    		int minIdle,
    		int maxWait,
    		long cacheTimeout,
    		Map<String, Long> mapCacheTimeout) {
		
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

        /* 默认配置 */
        RedisCacheConfiguration defaultCacheConfig = 
        		RedisCacheConfiguration
        		.defaultCacheConfig()
        		.entryTtl(Duration.ofSeconds(cacheTimeout))
        		/*.disableCachingNullValues()*/;
        
        Map<String, RedisCacheConfiguration> mapCache = new HashMap<String, RedisCacheConfiguration>();
        if (mapCacheTimeout != null && mapCacheTimeout.size() > 0) {
        	for (Entry<String, Long> entry : mapCacheTimeout.entrySet()) {
        		String key = entry.getKey();
        		Long timeout = entry.getValue();
        		if (key != null && key.trim().length() > 0 && timeout != null && timeout > 0) {
	        		RedisCacheConfiguration cache = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(timeout));/*.disableCachingNullValues()*/
	        		mapCache.put(key, cache);
        		}
        	}
        }
        
    	RedisCacheManager cacheManager = 
    			RedisCacheManager
    				.builder(RedisCacheWriter.lockingRedisCacheWriter(factory))
    				.cacheDefaults(defaultCacheConfig)
    				.withInitialCacheConfigurations(mapCache)
    				.transactionAware()
    				.build();
        
//        /* 配置account的超时时间为120s*/
//        Map<String, RedisCacheConfiguration> accountCache = 
//        		Collections.singletonMap("account", 
//        				RedisCacheConfiguration
//        					.defaultCacheConfig()
//        					.entryTtl(Duration.ofSeconds(120L))
//        					/*.disableCachingNullValues()*/);
//        //Map<String, RedisCacheConfiguration> mapCache2 = Collections.singletonMap("users", RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(50L)).disableCachingNullValues());
//        RedisCacheManager cacheManager = RedisCacheManager.builder(
//        		RedisCacheWriter.lockingRedisCacheWriter(factory))
//        		.cacheDefaults(defaultCacheConfig)
//        		.withInitialCacheConfigurations(accountCache)
//        		//.withInitialCacheConfigurations(mapCache2)
//        		.transactionAware()
//        		.build();

        return cacheManager;
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
