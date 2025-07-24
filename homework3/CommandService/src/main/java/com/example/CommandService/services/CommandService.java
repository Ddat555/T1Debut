package com.example.CommandService.services;

import com.example.CommandService.annotations.CommandValid;
import com.example.CommandService.annotations.WaylandWatchingYou;
import com.example.CommandService.configurations.PersonConfig;
import com.example.CommandService.exceptions.ErrorMaxThreadPoolExecutor;
import com.example.CommandService.models.CommandModel;
import com.example.CommandService.models.MetricModel;
import com.example.CommandService.models.TagModel;
import com.example.CommandService.runnableMethods.CommandRunnable;
import jakarta.annotation.PreDestroy;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class CommandService {

    private final ThreadPoolExecutor threadPoolExecutor;


    private final KafkaTemplate<String, MetricModel> kafkaTemplate;
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    private final MetricService metricService;


    public CommandService(MetricService metricService, PersonConfig personConfig, KafkaTemplate<String, MetricModel> kafkaTemplate) {
        this.metricService = metricService;

        this.kafkaTemplate = kafkaTemplate;
       int maxPoolThread = Integer.parseInt(personConfig.getUserConfigMap().get("MAX_THREAD"));
        threadPoolExecutor = new ThreadPoolExecutor(1,
                maxPoolThread,
                1000,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(maxPoolThread));
    }

    @WaylandWatchingYou
    public CompletableFuture<Void> executeCommand(@CommandValid CommandModel commandModel) {
        this.sendMetricThreadPool(1);

        try {
            switch (commandModel.getPriority()) {
                case COMMON:
                    CompletableFuture<Void> future = new CompletableFuture<>();

                    this.threadPoolExecutor.execute(() -> {
                        try {
                            (new CommandRunnable(commandModel)).run();
                            future.complete(null);
                        } catch (Throwable t) {
                            future.completeExceptionally(t);
                        } finally {
                            this.sendMetricThreadPool(-1);
                            this.sendMetricAuthor(commandModel.getAuthor());
                        }
                    });

                    return future;

                case CRITICAL:
                    try {
                        (new CommandRunnable(commandModel)).run();
                        return CompletableFuture.completedFuture(null);
                    } catch (Throwable t) {
                        return CompletableFuture.failedFuture(t);
                    } finally {
                        this.sendMetricThreadPool(-1);
                        this.sendMetricAuthor(commandModel.getAuthor());
                    }
            }
        } catch (Throwable var6) {
            this.sendMetricThreadPool(-1);
            return CompletableFuture.failedFuture(var6);
        }

        return CompletableFuture.completedFuture(null);
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

    private void sendMetricThreadPool(int delta) {
        var curSize = atomicInteger.addAndGet(delta);
        MetricModel model = new MetricModel("threadPool", curSize);
        kafkaTemplate.sendDefault(model);
    }

    private void sendMetricAuthor(String author) {
        List<TagModel> tagModelList = List.of(new TagModel("author", author));
        MetricModel metricModel = new MetricModel("author", 1.0);
        metricModel.setTagModels(tagModelList);
        metricService.sendMetric(metricModel);
    }
}
