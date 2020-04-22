package com.fnmusic.push.notification.service.messaging;

import com.fnmusic.base.messaging.impl.AbstractPublisher;
import com.fnmusic.base.models.AuditLog;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuditLogPublisher extends AbstractPublisher<AuditLog> {

    @Autowired
    RabbitTemplate auditRabbitTemplate;

    @Override
    public void init() {
        rabbitTemplate = auditRabbitTemplate;
    }
}
