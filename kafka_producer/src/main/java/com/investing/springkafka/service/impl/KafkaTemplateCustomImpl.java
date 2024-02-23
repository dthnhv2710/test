package com.investing.springkafka.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investing.springkafka.dto.request.Message;
import com.investing.springkafka.service.IKafkaTemplateCustom;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Component
public class KafkaTemplateCustomImpl implements IKafkaTemplateCustom {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void sendMessage(Message message) {
            String topic;
            if (Objects.equals(message.getContent().split("\\|")[1], "1")) {
                topic = "topicOne";
            } else if (Objects.equals(message.getContent().split("\\|")[1], "2")) {
                topic = "topicTwo";
            } else {
                topic = "topicThree";
            }
            String key = message.getContent().split("\\|")[2];
            String value = message.getContent();
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, value);
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(record);
            future.addCallback(
                    (result -> {
                        // doan nay out ra ngo xem the nao thoi
                        System.out.println("Sent message = [" + message.getContent() + "] " +
                            "to topic = [" + result.getRecordMetadata().topic()+ "] " +
                            "with offset = [" + result.getRecordMetadata().offset() + "]; " +
                            "partition = [" + result.getRecordMetadata().partition() + "]; " +
                            "timestamp = [" + result.getRecordMetadata().timestamp() + "]; "
                        );
                    }), ex -> {
                        System.err.println(ex.getMessage());
            });
    }
}
