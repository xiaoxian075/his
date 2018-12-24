package com.tf.sdk.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Set;

@Aspect
@Component
public class CacheRemoveAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@AfterReturning("@annotation(com.his.sdk.aop.CacheRemove)")
	public void remove(JoinPoint point) {
		Method method = ((MethodSignature) point.getSignature()).getMethod();
		CacheRemove cacheRemove = method.getAnnotation(CacheRemove.class);

		String value = cacheRemove.value();
		if (value != null && value.trim().length() > 0) {
			if (value.contains("#")) {
				value = parseKey(value, method, point.getArgs());
			}

			Set<String> deleteKeys = stringRedisTemplate.keys(value);
			if (deleteKeys != null && deleteKeys.size() > 0) {
				stringRedisTemplate.delete(deleteKeys);
			}
		} else {
			String[] keys = cacheRemove.regex();
			if (keys != null && keys.length > 0) {
				for (String key : keys) {
					try{
						if (key.contains("#")) {
							key = parseKey(key, method, point.getArgs());
						}

						Set<String> deleteKeys = stringRedisTemplate.keys(key);
						if (deleteKeys != null && deleteKeys.size() > 0) {
							stringRedisTemplate.delete(deleteKeys);
						}
					}catch (Exception e){
						// ignore
						logger.error("清除缓存报错！key="+key,e);
					}
				}
			}
		}
	}

    /**
     * parseKey from SPEL
     */
    private String parseKey(String key, Method method, Object [] args){
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();

        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
            context.setVariable("p"+i,args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }
}