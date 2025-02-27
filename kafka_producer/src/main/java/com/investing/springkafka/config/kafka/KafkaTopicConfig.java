package com.investing.springkafka.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Value(value = "${spring.kafka.replication-factor}")
    private short replicationFactor;

    @Value(value = "${spring.kafka.number-partitions}")
    private int numPartitions;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicOne() {
        return TopicBuilder.name("topicOne")
                .partitions(numPartitions)
                .replicas(replicationFactor)
                .build();
    }

    @Bean
    public NewTopic topicTwo() {
        return TopicBuilder.name("topicTwo")
                .partitions(numPartitions)
                .replicas(replicationFactor)
                .build();
    }

    @Bean
    public NewTopic topicThree() {
        return TopicBuilder.name("topicThree")
                .partitions(10)
                .replicas(replicationFactor)
                .build();
    }
}
