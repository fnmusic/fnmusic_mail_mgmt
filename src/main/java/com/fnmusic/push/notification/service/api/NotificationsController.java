package com.fnmusic.push.notification.service.api;

import com.fnmusic.base.models.Notification;
import com.fnmusic.base.models.Result;
import com.fnmusic.base.models.User;
import com.fnmusic.base.utils.SystemUtils;
import com.fnmusic.push.notification.service.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/fn/music/push/notification/service/notification")
public class NotificationsController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public Result<Notification> retrieveUnreadNotifications(@RequestHeader("PageNumber") int pageNumber, @RequestHeader("PageSize") int pageSize) {
        User currentUser = SystemUtils.getCurrentUser();
        Result<Notification> result = notificationService.retrieveUnreadNotifications(currentUser.getId().toString(),pageNumber,pageSize);
        return result;
    }
}
