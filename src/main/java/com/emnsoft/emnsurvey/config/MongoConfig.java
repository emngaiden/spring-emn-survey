package com.emnsoft.emnsurvey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.emnsoft.emnsurvey.repository")
public class MongoConfig {
    
}
