package com.fnmusic.push.notification.service.repository;

import com.fnmusic.base.models.SMS;
import com.fnmusic.base.repository.impl.AbstractMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SMSRepository extends AbstractMongoRepository<SMS> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void init() {
        abstractMongoTemplate = mongoTemplate;
        type = SMS.class;
    }
}
