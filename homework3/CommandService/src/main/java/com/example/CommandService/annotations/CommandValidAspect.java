package com.example.CommandService.annotations;

import com.example.CommandService.models.CommandModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

@Aspect
public class CommandValidAspect {
    private final Validator validator;

    public CommandValidAspect(Validator validator) {
        this.validator = validator;
    }

    @Around("execution(* *(.., @com.example.CommandService.annotations.CommandValid (*), ..))")
    public Object validateParams(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < args.length; i++) {
            for (Annotation annotation : paramAnnotations[i]) {
                if (annotation.annotationType().equals(CommandValid.class)) {
                    Set<ConstraintViolation<Object>> violations = validator.validate(args[i]);
                    if (!violations.isEmpty()) {
                        throw new IllegalArgumentException("Validation failed: " + violations);
                    }
                }
            }
        }

        return joinPoint.proceed();
    }

}
