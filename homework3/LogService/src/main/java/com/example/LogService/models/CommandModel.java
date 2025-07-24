package com.example.LogService.models;

import com.example.LogService.enums.Priority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommandModel {
    private String description;
    private Priority priority;
    private String author;
    private String time;


    @Override
    public String toString() {
        return "CommandModel{" +
                "description='" + description + '\'' +
                ", priority=" + priority +
                ", author='" + author + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
