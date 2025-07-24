package com.example.testDependencyService.controllers;

import com.example.CommandService.models.CommandModel;
import com.example.CommandService.services.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    @Autowired
    private CommandService commandService;

    @PostMapping
    public ResponseEntity<?> postTest(@RequestBody CommandModel commandModel) throws ExecutionException, InterruptedException {
        commandService.executeCommand(commandModel).get();
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
