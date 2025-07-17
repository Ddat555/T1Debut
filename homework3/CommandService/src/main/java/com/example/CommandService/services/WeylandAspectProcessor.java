package com.example.CommandService.services;

import com.example.CommandService.annotations.WeylandWatchingYou;
import com.example.CommandService.models.CommandModel;
import com.example.CommandService.models.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Component
@Aspect
public class WeylandAspectProcessor {

    @Autowired(required = false)
    private KafkaTemplate<String, LogModel> kafkaTemplate;

    @Value("${log.console}")
    private boolean isConsoleEnabled;

    @Around("@annotation(com.example.CommandService.annotations.WeylandWatchingYou)")
    public Object weylandWatchingYouMethod(ProceedingJoinPoint proceedingJoinPoint){
        LogModel logModel = new LogModel();
        logModel.setMethodName(proceedingJoinPoint.getSignature().getName());
        CommandModel commandModel = getCommandModel(proceedingJoinPoint.getArgs());
        logModel.setCommandModel(commandModel);
        logModel.setTime(LocalDateTime.now().toString());
        try {
            Object result = proceedingJoinPoint.proceed();
            logModel.setResult("SUCCESS");
            return result;
        } catch (Throwable e) {
            logModel.setResult("FAILURE");
            throw new RuntimeException(e);
        }
        finally {
            sendAudit(logModel);
        }
    }

    private CommandModel getCommandModel(Object[] args){
        for(Object object : args){
            if(object instanceof CommandModel)
                return (CommandModel) object;
        }
        return null;
    }

    private void sendAudit(LogModel logModel){
        if(isConsoleEnabled){
            System.out.println(logModel);
        }
        else{
            try {
                kafkaTemplate.sendDefault(logModel);
            }
            catch (NullPointerException e){
                throw new NullPointerException();
            }
        }

    }
}
