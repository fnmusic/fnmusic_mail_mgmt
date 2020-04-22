package com.fnmusic.push.notification.service.messaging;

import com.fnmusic.base.messaging.impl.AbstractListener;
import com.fnmusic.base.models.SMS;
import com.fnmusic.push.notification.service.services.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SMSListener extends AbstractListener<SMS> {

    @Autowired
    SMSService smsService;

    @Override
    public void init() {
        type = SMS.class;
    }

    @Override
    public void handleMessage(SMS sms) {
        smsService.sendSMS(sms);
    }
}
