package com.example.service1;

import com.example.service1.models.WeatherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

    @Autowired
    private KafkaTemplate<String, WeatherModel> template;

    public void sendMessage(WeatherModel weatherModel, String topicName) {
        template.send(topicName, weatherModel);
    }
}
