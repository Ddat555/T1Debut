package com.example.MetricService.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MetricModel {
    private String name;
    private double value;
    private Map<String, String> tags = new HashMap<>();
    private LocalDateTime localDateTime;

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
