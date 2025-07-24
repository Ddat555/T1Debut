package com.example.MetricService.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricModel {
    private String name;
    private double value;
    private List<TagModel> tagModels;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
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
