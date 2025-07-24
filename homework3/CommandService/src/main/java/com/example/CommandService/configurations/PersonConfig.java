package com.example.CommandService.configurations;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Getter
@Component
public class PersonConfig {

    private final HashMap<String, String> userConfigMap = new HashMap<>();

    public PersonConfig() {
        userConfigMap.put("LOG_KAFKA_TOPIC_NAME", "log");
        userConfigMap.put("METRIC_KAFKA_TOPIC_NAME", "metric");
        userConfigMap.put("LOG_CONSOLE_ENABLED", "TRUE");
        userConfigMap.put("MAX_THREAD", "10");
    }
}
