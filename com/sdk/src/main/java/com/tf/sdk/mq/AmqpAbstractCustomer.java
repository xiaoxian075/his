package com.tf.sdk.mq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import com.rabbitmq.client.Channel;

public class AmqpAbstractCustomer {
	
//	private String mqKey;
//	private MqReceiver receiver;
	
//	protected AmqpAbstractCustomer(String mqKey, MqReceiver receiver) {
//		this.mqKey = mqKey;
//		this.receiver = receiver;
//	}

	protected DirectExchange defaultExchange(String mqKey) {
		String exchange = AmqpUtil.getExchange(mqKey);
		return new DirectExchange(exchange);
	}

	protected Queue queue(String mqKey) {
		String queueName = AmqpUtil.getQueue(mqKey);
		return new Queue(queueName, true); //队列持久
	}

	protected Binding binding(Queue queue, DirectExchange exchange, String mqKey) {
		String routingkey = AmqpUtil.getRoutingkey(mqKey);
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}
 
    protected SimpleMessageListenerContainer messageContainer(
    		ConnectionFactory factory,
    		Queue queue,
			IMqReceive receiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        container.setQueues(queue);
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认
        container.setMessageListener(new ChannelAwareMessageListener() {
 
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                byte[] data = message.getBody();
                if (data != null && data.length > 0) {
                	String body = new String(data);
                	if (body != null && body.length() > 0) {
                		receiver.onMessage(body);
                	}
                }
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费
            }
        });
        return container;
    }
}
