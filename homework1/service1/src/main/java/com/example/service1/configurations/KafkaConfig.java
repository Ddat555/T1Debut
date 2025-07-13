package com.example.service1.configurations;

import com.example.service1.models.WeatherModel;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic newTopic() {
        return new NewTopic("weather", 1, (short) 1);
    }

    @Bean
    public ProducerFactory<String, WeatherModel> producerFactory(KafkaProperties kafkaProperties) {
        var map = kafkaProperties.buildProducerProperties();
        map.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        map.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return new DefaultKafkaProducerFactory<>(map);
    }

    @Bean
    public KafkaTemplate<String, WeatherModel> kafkaTemplate(ProducerFactory<String, WeatherModel> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
