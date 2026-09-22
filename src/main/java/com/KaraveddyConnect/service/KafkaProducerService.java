package com.KaraveddyConnect.service;

public interface KafkaProducerService {

    /**
     * emitToTopic
     * @param topicName of {@link String}
     * @param payload of {@link String}
     */
    public void emitToTopic(String topicName, String payload);
}
