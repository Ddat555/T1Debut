package com.example.authService.controllers;

import com.example.authService.models.dto.UserCreateDTO;
import com.example.authService.models.dto.UserLoginDTO;
import com.example.authService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUser(pageable));
    }

    @GetMapping("/getMy")
    public ResponseEntity<?> getMy(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMy());
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserCreateDTO userCreateDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.registryUser(userCreateDTO));
    }

    @PutMapping()
    public ResponseEntity<?> putUser(@RequestBody UserCreateDTO userCreateDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(userCreateDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO){
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(userLoginDTO));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(){
        userService.logout();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
