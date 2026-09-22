package com.KaraveddyConnect.service.impl;

import com.KaraveddyConnect.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 18:30 PM
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void emitToTopic(String topicName, String payload) {
        log.info("emitToTopic topicName {}, payload {} ", topicName, payload);
        try {
            kafkaTemplate.send(topicName, payload);
        } catch (Exception e) {
            log.error("Exception occurred while emitToTopic ", e);
        }
    }
}
