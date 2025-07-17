package com.example.MetricService.controllers;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetricController {

    @Autowired
    private MeterRegistry meterRegistry;


    @GetMapping("/actuator/prometheus")
    public ResponseEntity<?> getMetric(){
        return ResponseEntity.status(HttpStatus.OK).body((PrometheusMeterRegistry) meterRegistry).scrape())
    }
}
