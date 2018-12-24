package com.tf.sdk.mq;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class AmqpAbstractProducer /*implements IMqSend*/ {
	private final static Logger logger = LoggerFactory.getLogger(AmqpAbstractProducer.class);
	
	private RabbitTemplate rabbitTemplate;
	private String exchange;
	private String routingKey;
	 
    /**
          * 构造方法注入
     */
    protected AmqpAbstractProducer(RabbitTemplate rabbitTemplate, String exchange, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }
    
    protected AmqpAbstractProducer(RabbitTemplate rabbitTemplate, String mqKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = AmqpUtil.getExchange(mqKey);
        this.routingKey = AmqpUtil.getRoutingkey(mqKey);
    }
 
  
    //@Override
	protected boolean sendMsg(String content) {
    	try {
	    	if (StringUtils.isEmpty(content)) {
				return false;
			}
	        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
	        rabbitTemplate.convertAndSend(this.exchange, this.routingKey, content, correlationId);
    	} catch (Exception e) {
    		logger.error("短信推送失败，内容：" + content);
			logger.error("短信推送异常", e);
			return false;
    	}
        
        return true;
    }
}
