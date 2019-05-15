package com.fnmusic.mail.management.repository;

import com.fnmusic.base.models.Mail;
import com.fnmusic.base.repository.AbstractObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MailRepository extends AbstractObjectRepository<Mail> {

    @Autowired
    private MongoTemplate mailMongoTemplate;

    @Override
    public void init() {
        this.mongoTemplate = mailMongoTemplate;
        this.type = Mail.class;
    }
}
