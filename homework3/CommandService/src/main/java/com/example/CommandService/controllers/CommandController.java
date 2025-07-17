package com.example.CommandService.controllers;

import com.example.CommandService.models.CommandModel;
import com.example.CommandService.services.CommandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/command")
public class CommandController {

    @Autowired
    private CommandService commandService;

    @PostMapping
    public ResponseEntity<?> postCommand(@RequestBody @Valid CommandModel commandModel){
        commandService.executeCommand(commandModel);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
