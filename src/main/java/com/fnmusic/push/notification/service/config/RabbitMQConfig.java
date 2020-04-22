package com.fnmusic.push.notification.service.config;

import com.fnmusic.push.notification.service.messaging.MailListener;
import com.fnmusic.push.notification.service.messaging.NotificationListener;
import com.fnmusic.push.notification.service.messaging.SMSListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitMq.exchange}")
    private String exchange;
    @Value("${app.rabbitMq.mailRoutingKey}")
    private String mailRoutingKey;
    @Value("${app.rabbitMq.mailQueue}")
    private String mailQueue;
    @Value("${app.rabbitMq.smsRoutingKey}")
    private String smsRoutingKey;
    @Value("${app.rabbitMq.smsQueue}")
    private String smsQueue;
    @Value("${app.rabbitMq.notificationRoutingKey")
    private String notificationRoutingKey;
    @Value("${app.rabbitMq.notificationQueue}")
    private String notificationQueue;
    @Value("${app.rabbitMq.auditRoutingKey}")
    private String auditRoutingKey;
    @Value("${app.rabbitMq.auditQueue}")
    private String auditQueue;

    @Bean
    public Exchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Queue mailQueue(){
        return new Queue(mailQueue,true);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(smsQueue, true);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }

    @Bean Queue auditQueue() { return new Queue(auditQueue, true);}

    @Bean
    public Binding mailBinding(Queue mailQueue, TopicExchange exchange){
        return BindingBuilder
                .bind(mailQueue)
                .to(exchange)
                .with(mailRoutingKey);
    }

    @Bean
    public Binding smsBinding(Queue smsQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(smsQueue)
                .to(exchange)
                .with(smsRoutingKey);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange exchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(exchange)
                .with(notificationRoutingKey);
    }

    @Bean
    public Binding auditbinding(Queue auditQueue, TopicExchange exchange){
        return BindingBuilder
                .bind(auditQueue)
                .to(exchange)
                .with(auditRoutingKey);
    }

    @Bean(name = "auditTemplate")
    public RabbitTemplate auditRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(auditRoutingKey);

        return rabbitTemplate;
    }

    @Bean
    public MessageListenerAdapter mailListenerAdapter(MailListener listener) {
        return new MessageListenerAdapter(listener,"onListen");
    }

    @Bean
    public SimpleMessageListenerContainer mailContainer(ConnectionFactory connectionFactory, MessageListenerAdapter mailListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(mailQueue);
        container.setMessageListener(mailListenerAdapter);
        return container;
    }

    //sms
    @Bean MessageListenerAdapter smsListenerAdapter(SMSListener listener) {
        return new MessageListenerAdapter(listener,"onListen");
    }

    @Bean
    public SimpleMessageListenerContainer smsContainer(ConnectionFactory connectionFactory, MessageListenerAdapter smsListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(smsQueue);
        container.setMessageListener(smsListenerAdapter);
        return container;
    }

    //notification
    @Bean MessageListenerAdapter notificationListenerAdapter(NotificationListener listener) {
        return new MessageListenerAdapter(listener,"onListen");
    }

    @Bean
    public SimpleMessageListenerContainer notificationContainer(ConnectionFactory connectionFactory, MessageListenerAdapter notificationListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(notificationQueue);
        container.setMessageListener(notificationListenerAdapter);
        return container;
    }

}
