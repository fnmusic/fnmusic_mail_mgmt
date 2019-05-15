package com.fnmusic.mail.management.services;

import com.fnmusic.base.Utils.ConstantUtils;
import com.fnmusic.base.models.Mail;
import com.fnmusic.mail.management.utils.MailTemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    private MailTemplateUtils mailTemplateUtils;
    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    @PostConstruct
    private void init() {
        mailTemplateUtils = new MailTemplateUtils();
    }

    public void sendSimpleMessage(Mail mail) throws MailException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getMailTo());
        message.setSubject(mail.getMailSubject());
        message.setFrom(mail.getMailFrom());
        message.setCc(mail.getMailCc());
        message.setBcc(mail.getMailBcc());
        message.setText(mail.getBody());
        message.setSentDate(new Date());

        mailSender.send(message);
    }

    public void sendTemplatedMessage(Mail mail) throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(mail.getMailSubject());
        helper.setFrom(mail.getMailFrom());
        if (mail.getMailBcc() != null) {
            helper.setCc(mail.getMailCc());
        }

        if (mail.getMailBcc() != null) {
            helper.setBcc(mail.getMailBcc());
        }

        helper.setSentDate(new Date());

        if (mail.getTemplateName().equalsIgnoreCase(ConstantUtils.MAIL_TEMPLATE_NAMES[0])) {
            for (String recipient : mail.getMailTo()) {
                helper.setTo(recipient);
                message.setContent(mailTemplateUtils.getAccountActivationMailTemplate(mail,recipient),"text/html");
                mailSender.send(message);
            }
        }

    }


}
