package com.example.MetricService.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagModel {
    private String name;
    private String value;

    @Override
    public String toString() {
        return "TagModel{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
