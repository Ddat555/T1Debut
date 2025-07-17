package com.example.LogService.services;

import com.example.LogService.models.LogModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ListenService {

    private static final Logger log = LoggerFactory.getLogger(ListenService.class);

    @KafkaListener(topics = "log", groupId = "ddat111")
    public void listen(ConsumerRecord<String, LogModel> consumerRecord){
//        log.debug(consumerRecord.value().toString());
        System.out.println("SOPL: " + consumerRecord.value());
    }
}
