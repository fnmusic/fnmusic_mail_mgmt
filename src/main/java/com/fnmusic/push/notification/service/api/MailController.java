package com.fnmusic.push.notification.service.api;

import com.fnmusic.base.models.AuditLog;
import com.fnmusic.base.models.Mail;
import com.fnmusic.base.models.Result;
import com.fnmusic.base.models.User;
import com.fnmusic.base.utils.AuditLogType;
import com.fnmusic.base.utils.SystemUtils;
import com.fnmusic.push.notification.service.messaging.AuditLogPublisher;
import com.fnmusic.push.notification.service.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/fn/music/push/notification/service/mail")
public class MailController {

    @Autowired
    MailService mailService;
    @Autowired
    AuditLogPublisher auditLogPublisher;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Result<Mail> findById(@RequestHeader("Id") String id) {
        User currentUser = SystemUtils.getCurrentUser();

        Result<Mail> result =  mailService.retrieveById(id);
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId(currentUser.getId().toString());
        auditLog.setEvent("RETRIEVE MAIL BY ID");
        auditLog.setDescription(currentUser.getEmail() + "successfully retrieved mail log by id");
        auditLog.setAuditLogObject(result);
        auditLog.setAuditLogType(AuditLogType.USER);
        auditLog.setRole(currentUser.getRole());
        auditLogPublisher.publish(auditLog);

        return result;
    }
}
