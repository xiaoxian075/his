package com.his.yun.mq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.his.common.mq.chit.ChitSend;
import com.his.common.mq.log.LogSend;
import com.tf.sdk.mq.AmqpAbstractConfig;

@Configuration
public class MqConfig extends AmqpAbstractConfig {
	
	@Bean
	public ConnectionFactory connectionFactory(
			@Value("${spring.rabbitmq.host}") String host,
			@Value("${spring.rabbitmq.port}") int port,
			@Value("${spring.rabbitmq.username}") String username,
			@Value("${spring.rabbitmq.password}") String password) {
		return super.connectionFactory(host, port, username, password);
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
		return super.rabbitTemplate(factory);
	}
	
	@Bean
	public ChitSend chitSend() {
		return new ChitSend();
	}
	
	@Bean
	public LogSend logSend() {
		return new LogSend();
	}
}