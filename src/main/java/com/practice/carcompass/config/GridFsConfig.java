package com.practice.carcompass.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;

@Configuration
public class GridFsConfig {

    @Bean
    public GridFsTemplate gridFsTemplate(MongoTemplate mongoTemplate, MongoConverter mongoConverter) {
        return new GridFsTemplate(mongoTemplate.getMongoDatabaseFactory(), mongoConverter);
    }
}
