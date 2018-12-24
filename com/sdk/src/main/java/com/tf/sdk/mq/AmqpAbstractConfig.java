package com.tf.sdk.mq;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class AmqpAbstractConfig {
	
	protected RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate(factory);
        return template;
    }
	
	protected ConnectionFactory connectionFactory(String host, int port, String username, String password) {
		String addr = host + ":" + port;
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(addr);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);	        
		connectionFactory.setVirtualHost("/");
		connectionFactory.setPublisherConfirms(true); //必须要设置
		return connectionFactory;
	}
}
