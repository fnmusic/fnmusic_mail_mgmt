package com.fnmusic.push.notification.service.messaging;

import com.fnmusic.base.messaging.impl.AbstractListener;
import com.fnmusic.base.models.Notification;
import com.fnmusic.push.notification.service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener extends AbstractListener<Notification> {

    @Autowired
    NotificationService notificationService;

    @Override
    public void init() {
        type = Notification.class;
    }

    @Override
    public void handleMessage(Notification notification) {
        notificationService.save(notification);
    }
}
