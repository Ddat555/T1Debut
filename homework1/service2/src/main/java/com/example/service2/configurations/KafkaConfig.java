package com.example.service2.configurations;

import com.example.service2.models.WeatherModel;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConfig {

    @Bean
    public ConsumerFactory<String, WeatherModel> consumerFactory(KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(WeatherModel.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WeatherModel> kafkaListenerContainerFactory(
            ConsumerFactory<String, WeatherModel> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, WeatherModel> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
