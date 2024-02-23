package com.investing.springkafka.service;

import com.investing.springkafka.dto.request.Message;

public interface IKafkaTemplateCustom {

    void sendMessage(Message message);

}
