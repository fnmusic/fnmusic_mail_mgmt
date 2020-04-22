package com.fnmusic.push.notification.service.test;

import com.fnmusic.push.notification.service.services.MailService;
import org.junit.Before;
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


}
