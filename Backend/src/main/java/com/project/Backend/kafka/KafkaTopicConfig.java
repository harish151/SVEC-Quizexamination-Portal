package com.project.Backend.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@EnableKafka
@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name("student-create-topic")
                           .partitions(3)
                           .replicas(1)
                           .build();
    }

    @Bean
    public NewTopic orderTopic() {
        return TopicBuilder.name("result-topic")
                           .partitions(2)
                           .replicas(1)
                           .build();
    }

    @Bean
    public NewTopic paymentTopic() {
        return TopicBuilder.name("schedule-topic")
                           .partitions(1)
                           .replicas(1)
                           .build();
    }
}
