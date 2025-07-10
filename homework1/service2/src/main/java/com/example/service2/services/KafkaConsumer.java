package com.example.service2.services;

import com.example.service2.models.WeatherModel;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @Autowired
    private StatisticService statisticService;

    @KafkaListener(topics = "weather", groupId = "1")
    private void listen(ConsumerRecord<String, WeatherModel> record) {
//        System.out.println("Read: " + record.value());
        WeatherModel weatherModel = record.value();
//        System.out.println("Parse: " + weatherModel);
        if (weatherModel.isLast())
            statisticService.printStatistic();
        else
            statisticService.addWeatherInfo(weatherModel);
    }
}
