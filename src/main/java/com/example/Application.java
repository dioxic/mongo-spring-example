package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.messaging.DefaultMessageListenerContainer;
import org.springframework.data.mongodb.core.messaging.MessageListenerContainer;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableReactiveMongoRepositories
public class Application {

    //@Bean
    //MessageListenerContainer messageListenerContainer(MongoTemplate template) {
    //    return new DefaultMessageListenerContainer(template);
    //}

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
