package com.fnmusic.push.notification.service.repository;

import com.fnmusic.base.models.Mail;
import com.fnmusic.base.repository.impl.AbstractMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MailRepository extends AbstractMongoRepository<Mail> {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void init() {
        this.abstractMongoTemplate = mongoTemplate;
        this.type = Mail.class;
    }
}
