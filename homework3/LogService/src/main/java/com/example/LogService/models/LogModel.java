package com.example.LogService.models;


public class LogModel {
    private String methodName;
    private CommandModel commandModel;
    private String result;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public CommandModel getCommandModel() {
        return commandModel;
    }

    public void setCommandModel(CommandModel commandModel) {
        this.commandModel = commandModel;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

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
