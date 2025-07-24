package com.example.MetricService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;

@SpringBootApplication
public class MetricServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricServiceApplication.class, args);
	}

}
