package com.fnmusic.push.notification.service.config;

import com.fnmusic.push.notification.service.repository.MailRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = MailRepository.class)
@Configuration
public class MongoDBConfig {



}
