package com.example.CommandService.configurations;

import com.example.CommandService.annotations.CommandValidAspect;
import com.example.CommandService.models.LogModel;
import com.example.CommandService.models.MetricModel;
import com.example.CommandService.services.CommandService;
import com.example.CommandService.services.MetricService;
import com.example.CommandService.annotations.WaylandAspectProcessor;
import jakarta.validation.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class ApplicationBeanConfig {

    @Bean
    public CommandService commandService(MetricService metricService, PersonConfig personConfig, KafkaTemplate<String, MetricModel> kafkaTemplate) {
        return new CommandService(metricService, personConfig, kafkaTemplate);
    }

    @Bean
    public MetricService metricService(KafkaTemplate<String, MetricModel> kafkaTemplate) {
        return new MetricService(kafkaTemplate);
    }

    @Bean
    public WaylandAspectProcessor waylandAspectProcessor(PersonConfig personConfig, KafkaTemplate<String, LogModel> kafkaTemplate) {
        return new WaylandAspectProcessor(kafkaTemplate, personConfig);
    }

    @Bean
    public CommandValidAspect commandValidAspect(Validator validator){
        return new CommandValidAspect(validator);
    }
}
