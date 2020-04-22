package com.fnmusic.push.notification.service.services;

import com.fnmusic.base.models.SMS;
import com.fnmusic.push.notification.service.repository.SMSRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    @Autowired
    SMSRepository smsRepository;

    private static Logger logger = LoggerFactory.getLogger(SMSService.class);

    public void sendSMS(SMS sms) {
        save(sms);
    }

    private void save(SMS sms) {
        try {
            smsRepository.put(sms);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
