package com.example.CommandService.annotations;

import com.example.CommandService.configurations.PersonConfig;
import com.example.CommandService.models.CommandModel;
import com.example.CommandService.models.LogModel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;

@Aspect
public class WaylandAspectProcessor {

    private final KafkaTemplate<String, LogModel> kafkaTemplate;
    private final PersonConfig personConfig;

    public WaylandAspectProcessor(KafkaTemplate<String, LogModel> kafkaTemplate, PersonConfig personConfig) {
        this.kafkaTemplate = kafkaTemplate;
        this.personConfig = personConfig;
    }


    @Around("@annotation(com.example.CommandService.annotations.WeylandWatchingYou)")
    public Object weylandWatchingYouMethod(ProceedingJoinPoint proceedingJoinPoint) {
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
        } finally {
            sendAudit(logModel);
        }
    }

    private CommandModel getCommandModel(Object[] args) {
        for (Object object : args) {
            if (object instanceof CommandModel)
                return (CommandModel) object;
        }
        return null;
    }

    private void sendAudit(LogModel logModel) {
        if (personConfig.getUserConfigMap().get("LOG_CONSOLE_ENABLED").equals("TRUE")) {
            System.out.println(logModel);
        } else {
            try {
                kafkaTemplate.sendDefault(logModel);
            } catch (NullPointerException e) {
                throw new NullPointerException();
            }
        }
    }
}
