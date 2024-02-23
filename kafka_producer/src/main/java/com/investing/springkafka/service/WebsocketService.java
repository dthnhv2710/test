package com.investing.springkafka.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investing.springkafka.config.websocket.WebSocketClient;
import com.investing.springkafka.config.websocket.Websocket;
import com.investing.springkafka.dto.request.Message;
import com.investing.springkafka.dto.request.MstSec;
import com.investing.springkafka.service.impl.KafkaTemplateCustomImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class WebsocketService {

    private IMstService mstService;

    private KafkaTemplateCustomImpl kafkaTemplateCustom;

    @PostConstruct
    public void openConnect() throws Exception {
        Websocket websocket = new Websocket(kafkaTemplateCustom);
        if (websocket.isConnected()) {
            MstSec mstSecRequest = new MstSec();
            List<MstSec> lists = mstService.mstSecGetList(mstSecRequest);
            String listSecCd = lists.stream().map(MstSec::getSecCd).collect(Collectors.joining(";"));
            ObjectMapper objectMapper = new ObjectMapper();
            Message message = new Message("register.listen", "SYSTEM", listSecCd);
            websocket.sendMessage(objectMapper.writeValueAsString(message));
        }
    }
}
