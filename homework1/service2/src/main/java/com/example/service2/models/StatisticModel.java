package com.example.service2.models;

import lombok.Getter;
import lombok.Setter;

@Getter
public class StatisticModel {
    private String cityWithMaxRainDay;
    private int maxTemp;
    private String cityWithMaxTemp;
    private int minTemp;
    private String cityWithMinTemp;

    public void setMaxTemp(int temp, String city){
        maxTemp = temp;
        cityWithMaxTemp = city;
    }

    public void setMinTemp(int temp, String city){
        minTemp = temp;
        cityWithMinTemp = city;
    }

    public void setCityWithMaxRainDay(String cityWithMaxRainDay) {
        this.cityWithMaxRainDay = cityWithMaxRainDay;
    }
}
