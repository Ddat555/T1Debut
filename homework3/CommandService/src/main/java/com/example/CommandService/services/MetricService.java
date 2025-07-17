package com.example.CommandService.services;

import com.example.CommandService.models.MetricModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MetricService {

    @Autowired
    private KafkaTemplate<String, MetricModel> kafkaTemplate;

    public void sendMetric(String name, double value){
        MetricModel model = new MetricModel(name,value);
        kafkaTemplate.sendDefault(model);
    }

    public void sendMetric(String name, double value, Map<String, String> tags){
        MetricModel model = new MetricModel(name, value, tags);
        kafkaTemplate.sendDefault(model);
    }
}
