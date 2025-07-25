package com.example.authService.services;

import com.example.authService.repositories.AuthTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CleanTokenFromDatabaseService {

//    @Autowired
//    private AuthTokenRepository authTokenRepository;
//
//    @Scheduled(fixedRateString = "${schedule.interval}")
//    @Transactional
//    public void cleanDataBase(){
//        authTokenRepository.deleteExpiredTokens(Instant.now());
//    }
}
