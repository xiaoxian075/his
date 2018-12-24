package com.his.asyn.config;

import javax.annotation.Resource;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.his.asyn.elastic.ElasticService;
import com.his.asyn.log.LogService;
import com.his.asyn.mq.ChitReceiver;
import com.his.asyn.mq.ElasticReceiver;
import com.his.asyn.mq.LogReceiver;
import com.his.asyn.mq.MqReceiveAsyn;
import com.his.asyn.third.JuHeChit;
import com.tf.sdk.mq.AmqpAbstractCustomer;
import com.tf.sdk.mq.IMqReceive;

@Configuration
public class MqReceiveConfig extends AmqpAbstractCustomer {
	
	@Value("${spring.rabbitmq.receive.asyn}") 
	private String mqKey;
	
	@Resource
	private ElasticService elasticService;
	
	@Resource
	private LogService logService;

	 @Bean
	 public DirectExchange defaultExchange() {
	     return super.defaultExchange(mqKey);
	 }
	
	 @Bean
	 public Queue queue() {
	     return super.queue(mqKey);
	 }
	
	@Bean
	public Binding binding(Queue queue, DirectExchange directExchange) {
		return super.binding(queue, directExchange, mqKey);
	}
	
	@Bean
	public SimpleMessageListenerContainer messageContainer(ConnectionFactory factory, Queue queue, IMqReceive mqReceive) {
		return super.messageContainer(factory, queue, mqReceive);
	}

	@Bean
	public IMqReceive mqReceive(
			@Value("${third.sms.juhe.url}")String juheUrl,
			@Value("${third.sms.juhe.AppKey}")String juheAppKey) {
		JuHeChit juHeChit = new JuHeChit(juheUrl, juheAppKey);
		ChitReceiver chit = new ChitReceiver(juHeChit);
		ElasticReceiver elastic = new ElasticReceiver(elasticService);
		LogReceiver log = new LogReceiver(logService);
		return new MqReceiveAsyn(chit, elastic, log);
	}
	
}
