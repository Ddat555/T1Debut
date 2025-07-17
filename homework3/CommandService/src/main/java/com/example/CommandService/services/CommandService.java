package com.example.CommandService.services;

import com.example.CommandService.annotations.WeylandWatchingYou;
import com.example.CommandService.exceptions.ErrorMaxThreadPoolExecutor;
import com.example.CommandService.models.CommandModel;
import com.example.CommandService.models.MetricModel;
import com.example.CommandService.runnableMethods.CommandRunnable;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CommandService {

    private final ThreadPoolExecutor threadPoolExecutor;
    private final int maxPoolThread;

    @Autowired
    private KafkaTemplate<String, MetricModel> kafkaTemplate;
    private final AtomicInteger atomicInteger = new AtomicInteger(0);


    public CommandService(@Value("${command.maxThread}") int maxPoolThread) {
        this.maxPoolThread = maxPoolThread;
        threadPoolExecutor = new ThreadPoolExecutor(1,
                this.maxPoolThread,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(maxPoolThread));
    }

    @WeylandWatchingYou
    public void executeCommand(CommandModel commandModel) {
        sendMetricThreadPool(1);
        try {
            switch (commandModel.getPriority()) {
                case COMMON:
                    if (threadPoolExecutor.getQueue().size() >= maxPoolThread)
                        throw new ErrorMaxThreadPoolExecutor();
                    threadPoolExecutor.execute(() -> {
                        try {
                            new CommandRunnable(commandModel).run();
                        }
                        finally {
                            sendMetricThreadPool(-1);
                        }
                    });
                    break;
                case CRITICAL:
                    try {
                        new CommandRunnable(commandModel).run();
                    }
                    finally {
                        sendMetricThreadPool(-1);
                    }

                    break;
            }
        }
        catch (Throwable e){
            sendMetricThreadPool(-1);
        }
    }

    @PreDestroy
    public void preDestroy() {
        try {
            if (!threadPoolExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                threadPoolExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPoolExecutor.shutdownNow();
        }
    }

    private void sendMetricThreadPool(int delta){
        var curSize = atomicInteger.addAndGet(delta);
        MetricModel model = new MetricModel("threadPool", curSize);
        kafkaTemplate.sendDefault(model);
    }

    private void sendMetricAuthor(String author){

    }
}
