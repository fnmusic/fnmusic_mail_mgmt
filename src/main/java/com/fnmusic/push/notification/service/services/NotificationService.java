package com.fnmusic.push.notification.service.services;

import com.fnmusic.base.models.Notification;
import com.fnmusic.base.models.Result;
import com.fnmusic.base.utils.ConstantUtils;
import com.fnmusic.push.notification.service.repository.CacheRepository;
import com.fnmusic.push.notification.service.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class NotificationService {

    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    CacheRepository cacheRepository;

    static Logger logger = LoggerFactory.getLogger(NotificationService.class);
    static Object notificationCache;

    @PostConstruct
    public void init() {
        notificationCache = cacheRepository.createCache(ConstantUtils.APPNAME,"notificationCache",1L);
    }

    public void save(Notification notification) {
        try {
            notificationRepository.put(notification);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public Result<Notification> retrieveUnreadNotifications(String userId, int pageNumber, int pageSize) {
        try {
            String key = "retrieveUnreadNotifications_userId_"+userId+"_pageNumber_"+pageNumber+"_pageSize_"+pageSize+"";
            Result<Notification> data = (Result<Notification>) cacheRepository.get(notificationCache,key);
            if (data == null) {
                data = notificationRepository.getUnreadNotifications(userId,pageNumber,pageSize);
                if (!data.getList().isEmpty()) {
                    cacheRepository.put(notificationCache,key,data);
                }
            }
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }
}
