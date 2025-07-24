package com.example.CommandService.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MetricModel {
    private String name;
    private double value;
    private List<TagModel> tagModels;
    private LocalDateTime localDateTime;

    public MetricModel(String name, double value) {
        this.name = name;
        this.value = value;
        localDateTime = LocalDateTime.now();
    }


    @Override
    public String toString() {
        return "MetricModel{" +
                "name='" + name + '\'' +
                ", value=" + value +
                ", tagModels=" + tagModels +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
