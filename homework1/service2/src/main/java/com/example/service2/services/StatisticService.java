package com.example.service2.services;

import com.example.service2.models.StatisticModel;
import com.example.service2.models.WeatherModel;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StatisticService {
    private final Map<String, Queue<WeatherModel>> weatherMap;
    private final static int MAX_DAY = 7;

    public StatisticService() {
        weatherMap = new HashMap<>();
        List<String> citiesList = List.of("Moscow", "Samara", "Ufa", "Kazan", "Tver");
        citiesList.forEach(city -> weatherMap.put(city, new ArrayDeque<>()));
    }

    public void addWeatherInfo(WeatherModel weatherModel) {
        Queue<WeatherModel> queue = weatherMap.get(weatherModel.getCity());
        queue.add(weatherModel);
        if (queue.size() > MAX_DAY)
            queue.poll();
    }

    private void printQueue() {

        for (Queue<WeatherModel> weatherModels : weatherMap.values()) {
            if (!weatherModels.isEmpty()) {
                System.out.print(weatherModels.peek().getCity() + " : ");
                for (WeatherModel weatherModel : weatherModels) {
                    System.out.print(weatherModel.getTemp() + " " + weatherModel.getStatus().charAt(0) + " | ");
                }
                System.out.println();
            }
        }
    }

    public void printStatistic() {
        StatisticModel statisticModel = generateStatistic();
        printQueue();
        System.out.println("Самое большое количество дождливых дней за эту неделю в городе: " + statisticModel.getCityWithMaxRainDay());
        System.out.println("Самая жаркая погода была: " + statisticModel.getMaxTemp() + " в городе: " + statisticModel.getCityWithMaxTemp());
        System.out.println("Самая низкая температура была: " + statisticModel.getMinTemp() + " в городе: " + statisticModel.getCityWithMinTemp());
    }

    public StatisticModel generateStatistic() {
        StatisticModel statisticModel = new StatisticModel();
        statisticModel.setCityWithMaxRainDay(getCityWithMaxRainDay());
        WeatherModel temp = getWeatherModelWithMaxTemp();
        statisticModel.setMaxTemp(temp.getTemp(), temp.getCity());
        temp = getWeatherModelWithMinTemp();
        statisticModel.setMinTemp(temp.getTemp(), temp.getCity());
        return statisticModel;
    }

    private WeatherModel getWeatherModelWithMaxTemp() {
        WeatherModel modelWithMaxTemp = new WeatherModel();
        modelWithMaxTemp.setTemp(Integer.MIN_VALUE);
        for (Queue<WeatherModel> modelQueue : weatherMap.values()) {
            for (WeatherModel weatherModel : modelQueue) {
                if (weatherModel.getTemp() > modelWithMaxTemp.getTemp())
                    modelWithMaxTemp = weatherModel;
            }
        }
        return modelWithMaxTemp;
    }

    private String getCityWithMaxRainDay() {
        int maxCount = 0;
        String result = "";

        for (Queue<WeatherModel> modelQueue : weatherMap.values()) {
            int count = 0;
            for (WeatherModel weatherModel : modelQueue) {
                if (weatherModel.getStatus().equals("Rain"))
                    count++;
            }
            if (count > maxCount) {
                maxCount = count;
                result = modelQueue.peek().getCity();
            }
        }
        if (result.isEmpty())
            return "отсутствует";
        else
            return result;
    }

    private WeatherModel getWeatherModelWithMinTemp() {
        WeatherModel modelWithMinTemp = new WeatherModel();
        modelWithMinTemp.setTemp(Integer.MAX_VALUE);
        for (Queue<WeatherModel> modelQueue : weatherMap.values()) {
            for (WeatherModel weatherModel : modelQueue) {
                if (weatherModel.getTemp() < modelWithMinTemp.getTemp())
                    modelWithMinTemp = weatherModel;
            }
        }
        return modelWithMinTemp;
    }
}
