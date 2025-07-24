package com.example.CommandService.services;

import com.example.CommandService.models.MetricModel;
import org.springframework.kafka.core.KafkaTemplate;

public class MetricService {

    private final KafkaTemplate<String, MetricModel> kafkaTemplate;


    public MetricService(KafkaTemplate<String, MetricModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMetric(String name, double value) {
        try {
            MetricModel model = new MetricModel(name, value);
            kafkaTemplate.sendDefault(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendMetric(MetricModel model) {
        try {
            kafkaTemplate.sendDefault(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
