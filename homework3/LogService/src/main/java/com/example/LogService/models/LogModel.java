package com.example.LogService.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogModel {
    private String methodName;
    private CommandModel commandModel;
    private String result;
    private String time;


    @Override
    public String toString() {
        return "LogModel{" +
                "methodName='" + methodName + '\'' +
                ", commandModel=" + commandModel +
                ", result='" + result + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
