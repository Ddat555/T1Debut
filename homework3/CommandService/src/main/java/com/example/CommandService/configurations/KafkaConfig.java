package com.example.CommandService.configurations;

import com.example.CommandService.models.LogModel;
import com.example.CommandService.models.MetricModel;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
    @ConditionalOnProperty(value = "log.console", havingValue = "false", matchIfMissing = true)
    public ProducerFactory<String, LogModel> producerFactoryLog(KafkaProperties kafkaProperties) {
        var map = kafkaProperties.buildProducerProperties();
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(map);
    }

    @Bean
    public ProducerFactory<String, MetricModel> producerFactoryMetric(KafkaProperties kafkaProperties) {
        var map = kafkaProperties.buildProducerProperties();
        map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(map);
    }

    @Bean
    @ConditionalOnProperty(value = "log.console", havingValue = "false", matchIfMissing = true)
    public NewTopic newTopicLog() {
        return new NewTopic("log", 1, (short) 1);
    }

    @Bean
    public NewTopic newTopicMetric(){
        return new NewTopic("metric", 1, (short) 1);
    }

    @Bean
    @ConditionalOnProperty(value = "log.console", havingValue = "false", matchIfMissing = true)
    public KafkaTemplate<String, LogModel> kafkaTemplateLog(ProducerFactory<String, LogModel> producerFactory) {
        var kafkaTemp = new KafkaTemplate<>(producerFactory);
        kafkaTemp.setDefaultTopic("log");
        return kafkaTemp;
    }

    @Bean
    public KafkaTemplate<String, MetricModel> kafkaTemplateMetric(ProducerFactory<String, MetricModel> producerFactory){
        var kafkaTemp = new KafkaTemplate<>(producerFactory);
        kafkaTemp.setDefaultTopic("metric");
        return kafkaTemp;
    }



}
