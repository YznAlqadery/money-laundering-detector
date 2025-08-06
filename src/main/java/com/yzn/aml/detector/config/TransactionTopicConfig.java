package com.yzn.aml.detector.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TransactionTopicConfig {

    @Bean
    public NewTopic transactionTopic(){
        return TopicBuilder
                .name("transaction-topic")
                .build();
    }
}
