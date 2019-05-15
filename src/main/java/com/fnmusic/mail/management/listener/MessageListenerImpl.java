package com.fnmusic.mail.management.listener;

import com.fnmusic.base.models.Mail;
import com.fnmusic.mail.management.services.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Date;

@Component
public class MessageListenerImpl implements IMessageListener {

    @Autowired
    private MailService mailService;

    private static Logger logger = LoggerFactory.getLogger(MessageListenerImpl.class);

    public void onListen(Object object) throws IOException, ClassNotFoundException, MessagingException {

        if (!object.getClass().isAssignableFrom(byte[].class)) {
            logger.error("Unknown Object");
        }

        byte[] bytes = (byte[]) object;
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        ObjectInputStream is = new ObjectInputStream(in);
        Object mailObject = is.readObject();
        if (!mailObject.getClass().isAssignableFrom(Mail.class)) {
            logger.error("Incompatible Mail Object");
        }

        Mail mail = (Mail) mailObject;
        mailService.sendTemplatedMessage(mail);
        mail.setSentDate(new Date());
    }

}
