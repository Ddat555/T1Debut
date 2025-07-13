package com.example.service1.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherModel {
    private int temp;
    private String status;
    private String city;
    private LocalDate date;
    private LocalDateTime timeSendMessage;
    private boolean isLast;

    @Override
    public String toString() {
        return "WeatherModel{" +
                "temp=" + temp +
                ", status='" + status + '\'' +
                ", city='" + city + '\'' +
                ", date=" + date +
                ", timeSendMessage=" + timeSendMessage +
                ", isLast=" + isLast +
                '}';
    }
}
