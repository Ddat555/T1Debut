package com.example.MetricService.services;

import com.example.MetricService.models.MetricModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ListenService {

    @KafkaListener(topics = "metric", groupId = "ddat555")
    public void listen(ConsumerRecord<String, MetricModel> consumerRecord){
        System.out.println(consumerRecord.value());
    }
}
