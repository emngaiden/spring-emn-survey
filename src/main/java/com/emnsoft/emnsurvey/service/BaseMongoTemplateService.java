package com.emnsoft.emnsurvey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class BaseMongoTemplateService {

    @Autowired
    public MongoTemplate mongoTemplate;
}
