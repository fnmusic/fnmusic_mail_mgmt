package com.fnmusic.push.notification.service.messaging;

import com.fnmusic.base.messaging.impl.AbstractListener;
import com.fnmusic.base.models.Mail;
import com.fnmusic.push.notification.service.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailListener extends AbstractListener<Mail> {

    @Autowired
    MailService mailService;

    static Logger logger = LoggerFactory.getLogger(MailListener.class);

    @Override
    public void init() {
        type = Mail.class;
    }

    @Override
    public void handleMessage(Mail mail) {
        mailService.send(mail);
    }
}
