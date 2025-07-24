package com.example.MetricService.services;

import com.example.MetricService.models.dto.MetricModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ListenService {

    @Autowired
    private MetricService metricService;

    @KafkaListener(topics = "metric", groupId = "ddat555")
    public void listen(ConsumerRecord<String, MetricModel> consumerRecord) {
        MetricModel metricModel = consumerRecord.value();
        System.out.println(metricModel);
        metricService.saveMetricModel(metricModel);
    }
}
