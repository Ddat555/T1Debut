package com.example.CommandService.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MetricModel {
    private String name;
    private double value;
    private Map<String, String> tags = new HashMap<>();
    private LocalDateTime localDateTime;


    public MetricModel(){
        localDateTime = LocalDateTime.now();
    }

    public MetricModel(String name, double value){this.name = name; this.value = value; localDateTime = LocalDateTime.now();}

    public MetricModel(String name, double value, Map<String, String> tags) {
        this.name = name;
        this.value = value;
        this.tags = tags;
        localDateTime = LocalDateTime.now();
    }

    public void addTag(String key, String value){
        tags.put(key,value);
    }
}
