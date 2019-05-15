package com.fnmusic.mail.management.config;

import com.fnmusic.mail.management.listener.IMessageListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.mailqueue}")
    private String mailQueue;
    @Value("${app.rabbitmq.mailroutingkey}")
    private String mailRoutingKey;
    @Value("${app.rabbitmq.exchange}")
    private String exchange;

    @Bean
    public Exchange mailExchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue queue(){
        return new Queue(mailQueue,true);
    }

    @Bean
    public Binding mailBinding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(mailRoutingKey);
    }

    @Bean
    public SimpleMessageListenerContainer mailContainer(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(mailQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    public MessageListenerAdapter mailQueueListener(IMessageListener listener) {
        return new MessageListenerAdapter(listener,"onListen");
    }

}
