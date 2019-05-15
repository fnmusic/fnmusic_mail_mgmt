package com.fnmusic.mail.management.test;

import com.fnmusic.base.models.Mail;
import com.fnmusic.mail.management.services.MailService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailTest {

    @Autowired
    MailService mailSenderService;

    private static Logger logger = LoggerFactory.getLogger(MailTest.class);

    @Before
    public void init() {

    }

    @Test
    public void sendMessage() {
        boolean isSent = false;
        Mail mail = new Mail();
        mail.setMailTo(new String[] {
                "senunwah@yahoo.com"
        });

        mail.setMailSubject("Test Mail");
        mail.setBody("Mail was received successfully");
        try {
            mailSenderService.sendSimpleMessage(mail);
            isSent = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            Assert.fail("Unable to send mail");
        }

        Assert.assertTrue(isSent);
    }
}
