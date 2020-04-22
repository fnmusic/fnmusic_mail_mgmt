package com.fnmusic.push.notification.service.services;

import com.fnmusic.base.models.Mail;
import com.fnmusic.base.models.Result;
import com.fnmusic.base.utils.ConstantUtils;
import com.fnmusic.push.notification.service.repository.CacheRepository;
import com.fnmusic.push.notification.service.repository.MailRepository;
import com.fnmusic.push.notification.service.utils.MailTemplateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
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
    @Autowired
    private MailRepository mailRepository;
    @Autowired
    private CacheRepository cacheRepository;
    @Autowired
    private MailTemplateUtils mailTemplateUtils;


    private static Object mailCache;
    private static Logger logger = LoggerFactory.getLogger(MailService.class);

    @PostConstruct
    private void init() {
        mailCache = cacheRepository.createCache(ConstantUtils.APPNAME,"mailCache",1L);
    }

    public void send(Mail mail) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(mail.getTo());

            if (mail.getCc() != null) {
                helper.setCc(mail.getCc());
            }

            if (mail.getBcc() != null) {
                helper.setBcc(mail.getBcc());
            }

            helper.setSubject(mail.getSubject());
            helper.setText(mail.getText());
            mailSender.send(message);

            mail.setSent(true);
            mail.setDateSent(new Date());
        } catch (MailException | MessagingException e) {
            mail.setSent(false);
            logger.error(e.getMessage());
        } finally {
            save(mail);
        }
    }

    private void save(Mail mail) {
        try {
            mailRepository.put(mail);
            clearFromRedisCache();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public Result<Mail> retrieveById(String id) {
        try{
            String key = "mail_retrieveById_id_"+id+"";
            Result<Mail> data = (Result<Mail>) cacheRepository.get(mailCache,key);
            if (data == null) {
                data = mailRepository.getById(id);
                if (data != null) {
                    cacheRepository.put(mailCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Result<Mail> retrieveAllByUserId(long userId, int pageNumber, int pageSize) {

        try {
            String key = "mail_retrieveAllByUserId_userId_"+userId+"_pageNumber_"+pageNumber+"_pageSize_"+pageSize+"";
            Result<Mail> data = (Result<Mail>) cacheRepository.get(mailCache,key);
            if (data == null) {
                data = mailRepository.getAllByUniquekey(userId,pageNumber,pageSize);
                if (data != null) {
                    cacheRepository.put(mailCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Result<Mail> retrieveAllByUserIdOrderedDateRange(long userId, int pageNumber, int pageSize, int from, int to) {

        try {
            String key = "mail_retrieveAllByUserIdOrderedDateRange_userId_"+userId+"_pageNumber_"+pageNumber+"_pageSize_"+pageSize+"_from_"+from+"_to_"+to+"";
            Result<Mail> data = (Result<Mail>) cacheRepository.get(mailCache,key);
            if (data == null) {
                data = mailRepository.getAllByUniqueKeyOrderedByDateRange(userId,pageNumber,pageSize,from,to);
                if (data != null) {
                    cacheRepository.put(mailCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Result<Mail> retrieveAll(int pageNumber, int pageSize) {

        try {
            String key = "mail_retrieveAll_pageNumber_"+pageNumber+"_pageSize_"+pageSize+"";
            Result<Mail> data = (Result<Mail>) cacheRepository.get(mailCache,key);
            if (data == null) {
                data = mailRepository.getAll(pageNumber,pageSize);
                if (data.getList() != null) {
                    cacheRepository.put(mailCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Result<Mail> retrieveAllByDateRange(int pageNumber, int pageSize, int from, int to) {

        try {
            String key = "mail_retrieveAllByDateRange_pageNumber_"+pageNumber+"_pageSize_"+pageSize+"_from_"+from+"_to"+to+"";
            Result<Mail> data = (Result<Mail>) cacheRepository.get(mailCache,key);
            if (data == null) {
                data = mailRepository.getAllByDateRange(pageNumber,pageSize,from,to);
                if (data.getList() != null) {
                    cacheRepository.put(mailCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return null;
    }

    public void update(Mail mail) {

        try {
            mailRepository.update(mail);
            clearFromRedisCache();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void deleteById(String id) {

        try {
            mailRepository.removeById(id);
            clearFromRedisCache();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void clearFromRedisCache() {
        cacheRepository.clear(mailCache,"mail_*");
    }

    private void ServiceCheck() {

    }
}
