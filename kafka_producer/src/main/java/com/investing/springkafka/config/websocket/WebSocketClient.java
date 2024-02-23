package com.investing.springkafka.config.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investing.springkafka.dto.request.Message;
import com.investing.springkafka.service.WebsocketService;
import com.investing.springkafka.service.impl.KafkaTemplateCustomImpl;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class WebSocketClient {

    private Session session = null;

    private final KafkaTemplateCustomImpl kafkaTemplateCustom;

    private String message;

    @Value("${websocket.url}")
    private String URL_WS;

    @Value("${websocket.delay-reconnect}")
    private long DELAY_RECONNECT;


    public WebSocketClient(URI endpointURI, KafkaTemplateCustomImpl kafkaTemplateCustom) {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpointURI);
            this.kafkaTemplateCustom = kafkaTemplateCustom;
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        try {
            System.out.println("Connected");
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            // TODO: log
        }
    }

    @OnClose
    public void onClose(Session session) {
        try {
            System.out.println("Closed");
            Timer timer = new Timer();
            timer.schedule(new AutoReconnect(), DELAY_RECONNECT);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            // TODO: log
        }
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Message messageReceive = objectMapper.readValue(message, Message.class);
            kafkaTemplateCustom.sendMessage(messageReceive);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            // TODO: log
        }
    }

    public void sendMessage(String message) {
        if (session != null) {
            this.message = message;
            this.session.getAsyncRemote().sendText(message);
        } else {
            System.err.println("send fail");
            // TODO: log
        }
    }

    public boolean isSessionConnected() {
        return this.session != null;
    }

    private class AutoReconnect extends TimerTask {
        @Override
        public void run() {
                while (!(session != null && session.isOpen())) {
                    WebSocketClient webSocketClient;
                    try {
                        TimeUnit.MILLISECONDS.sleep(DELAY_RECONNECT);
                        webSocketClient = new WebSocketClient(new URI(URL_WS), kafkaTemplateCustom);
                    } catch (RuntimeException | URISyntaxException | InterruptedException ex) {
                        System.err.println(ex);
                        continue;
                    }
                    webSocketClient.session.getAsyncRemote().sendText(message);
                    session = webSocketClient.session;
                }
        }
    }
}
