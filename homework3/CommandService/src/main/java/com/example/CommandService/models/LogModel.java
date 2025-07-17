package com.example.CommandService.models;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
