package com.example.vouchermanagement.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.example.vouchermanagement")
public class MongoRepositoriesConfig {
}
