package com.fnmusic.mail.management.config;

import com.fnmusic.base.Repository.MailRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = MailRepository.class)
@Configuration
public class MongoDBConfig {



}
