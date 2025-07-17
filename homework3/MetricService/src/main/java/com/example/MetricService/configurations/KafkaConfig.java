package com.example.MetricService.configurations;

import com.example.MetricService.models.MetricModel;
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
    public ConsumerFactory<String, MetricModel> consumerFactory(KafkaProperties kafkaProperties){
        var map = kafkaProperties.buildConsumerProperties();
        return new DefaultKafkaConsumerFactory<>(map, new StringDeserializer(), new JsonDeserializer<>(MetricModel.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MetricModel> concurrentKafkaListenerContainerFactory(ConsumerFactory<String, MetricModel> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, MetricModel> concurrentKafkaListenerContainerFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(consumerFactory);
        return concurrentKafkaListenerContainerFactory;
    }
}
