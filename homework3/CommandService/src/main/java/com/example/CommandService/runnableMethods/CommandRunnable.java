package com.example.CommandService.runnableMethods;

import com.example.CommandService.models.CommandModel;

public class CommandRunnable implements Runnable{

    private final CommandModel commandModel;

    public CommandRunnable(CommandModel commandModel){
        this.commandModel = commandModel;
    }
    @Override
    public void run() {
        try {
//            System.out.printf("%s : Выполнение: %s | Время: %s | Приоритет: %s%n", commandModel.getAuthor(), commandModel.getDescription(), commandModel.getTime(), commandModel.getPriority());
            Thread.sleep(2000);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }


    }
}
