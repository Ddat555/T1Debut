package com.example.LogService.configurations;

import com.example.LogService.models.LogModel;
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
    public ConsumerFactory<String, LogModel> consumerFactory(KafkaProperties kafkaProperties) {
        var map = kafkaProperties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(map, new StringDeserializer(), new JsonDeserializer<>(LogModel.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LogModel> concurrentKafkaListenerContainerFactory(ConsumerFactory<String, LogModel> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, LogModel> concurrentKafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, LogModel> kafkaListenerContainerFactory(
            ConsumerFactory<String, LogModel> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, LogModel> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
