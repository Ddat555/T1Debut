package com.example.service1;

import com.example.service1.models.WeatherModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class WeatherService {
    @Autowired
    private KafkaProducer kafkaProducer;

    @Value("${weather.schedule.maxDays}")
    private int maxDays;

    private LocalDate currentDay;

    public WeatherService(){
        currentDay = LocalDate.now();

    }

    private final Random rnd = new Random();
    private final List<String> citiesList = List.of("Moscow","Samara", "Ufa", "Kazan", "Tver");
    private final List<String> weatherStatus = List.of("Sun", "Rain", "Cloudy");

    @Scheduled(fixedRateString = "${weather.schedule.interval}")
    public void generateWeather(){
        for(LocalDate localDate = currentDay; localDate.isBefore(currentDay.plusDays(maxDays)); localDate = localDate.plusDays(1)){
            for(String city: citiesList){
                WeatherModel weatherModel = new WeatherModel();
                weatherModel.setCity(city);
                weatherModel.setStatus(weatherStatus.get(rnd.nextInt(weatherStatus.size())));
                weatherModel.setTemp(rnd.nextInt(0,35));
                weatherModel.setTimeSendMessage(LocalDateTime.now());
                weatherModel.setDate(localDate);
                weatherModel.setLast(false);
                kafkaProducer.sendMessage(weatherModel, "weather");
            }
        }
        currentDay = currentDay.plusDays(maxDays);
        WeatherModel weatherModel = new WeatherModel();
        weatherModel.setLast(true);
        kafkaProducer.sendMessage(weatherModel, "weather");
    }
}
