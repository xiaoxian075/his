package com.tf.sdk.mq;

import java.util.UUID;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class AmqpAbstractProducerCallback implements RabbitTemplate.ConfirmCallback {
	
	private RabbitTemplate rabbitTemplate;
	private String exchange;
	private String routingKey;
	 
    /**
     * 构造方法注入
     */
    protected AmqpAbstractProducerCallback(RabbitTemplate rabbitTemplate, String exchange, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
        rabbitTemplate.setConfirmCallback(this); //rabbitTemplate如果为单例的话，那回调就是最后设置的内容
    }
 
    protected void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(this.exchange, this.routingKey, content, correlationId);
    }
    
	  /**
	  * 回调
	  */
	 @Override
	 public void confirm(CorrelationData correlationData, boolean ack, String cause) {
//	     System.out.println(" 回调id:" + correlationData);
//		 if (ack) {
//		     System.out.println("消息成功消费");
//		 } else {
//			 System.out.println("消息消费失败:" + cause);
//	     }
	 }    
}
