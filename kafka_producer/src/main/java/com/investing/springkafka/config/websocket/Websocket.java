package com.investing.springkafka.config.websocket;

import com.investing.springkafka.service.impl.KafkaTemplateCustomImpl;

import java.net.URI;

public class Websocket {

    WebSocketClient webSocketClient;

    public Websocket(KafkaTemplateCustomImpl kafkaTemplateCustom) {
        try{
            this.webSocketClient = new WebSocketClient(new URI("ws://192.168.124.210:3000/"), kafkaTemplateCustom);
        } catch (Exception ex){
            System.err.println(ex.getMessage());
            // TODO: log, handle ex
        }
    }

    public Boolean isConnected() {
       return this.webSocketClient.isSessionConnected();
    }

    public Boolean isDisconnected() {
        return !this.webSocketClient.isSessionConnected();
    }

    public void sendMessage(String msg) {
        this.webSocketClient.sendMessage(msg);
    }
}
