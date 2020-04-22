package com.fnmusic.push.notification.service.repository;

import com.fnmusic.base.models.Notification;
import com.fnmusic.base.models.Result;
import com.fnmusic.base.repository.impl.AbstractMongoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class NotificationRepository extends AbstractMongoRepository<Notification> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public void init() {
        abstractMongoTemplate = mongoTemplate;
        type = Notification.class;
    }

    @Transactional
    public Result<Notification> getUnreadNotifications(String userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Query query = new Query().with(pageable);
        query.addCriteria(Criteria.where("userId").is(userId).and("read").is("false"));
        List<Notification> list = mongoTemplate.find(query, this.type);
        return new Result<>(0,list, (long) list.size(),pageNumber,pageSize);
    }
}
