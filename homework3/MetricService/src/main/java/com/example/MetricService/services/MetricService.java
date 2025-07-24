package com.example.MetricService.services;

import com.example.MetricService.models.MetricModelEntity;
import com.example.MetricService.models.TagModelEntity;
import com.example.MetricService.models.dto.MetricModel;
import com.example.MetricService.models.dto.TagModel;
import com.example.MetricService.repositories.MetricRepository;
import com.example.MetricService.repositories.TagRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class MetricService {

    private final MetricRepository metricRepository;
    private final TagRepository tagRepository;
    private final ConverterDTOService converterDTOService;

    private final MeterRegistry meterRegistry;
    private final AtomicInteger threadPoolLoad;

    public MetricService(MetricRepository metricRepository, TagRepository tagRepository, ConverterDTOService converterDTOService, MeterRegistry meterRegistry) {
        this.metricRepository = metricRepository;
        this.tagRepository = tagRepository;
        this.converterDTOService = converterDTOService;
        this.meterRegistry = meterRegistry;

        this.threadPoolLoad = new AtomicInteger(0);
        meterRegistry.gauge("threadPool", threadPoolLoad);

    }

    @Transactional
    public void saveMetricModel(MetricModel metricModel) {
        if (metricModel.getName().equals("threadPool"))
            threadPoolLoad.set((int) metricModel.getValue());
        else if (metricModel.getName().equals("author")) {
            String authorTagValue = metricModel.getTagModels().stream()
                    .filter(t -> "author".equals(t.getName()))
                    .map(TagModel::getValue)
                    .findFirst()
                    .orElse("guest");

            Counter counter = Counter.builder("method_call_total")
                    .description("Total number of method calls per user")
                    .tags("user", authorTagValue)
                    .register(meterRegistry);
            counter.increment();
        }

        List<TagModelEntity> tagModelEntityList = new ArrayList<>();
        MetricModelEntity metricModelEntity = new MetricModelEntity(metricModel.getName(), metricModel.getValue(), metricModel.getLocalDateTime());
        metricModelEntity = metricRepository.save(metricModelEntity);
        if (metricModel.getTagModels() != null) {
            for (TagModel tagModel : metricModel.getTagModels()) {
                TagModelEntity tagModelEntity = new TagModelEntity(tagModel.getName(), tagModel.getValue(), metricModelEntity);
                tagModelEntity = tagRepository.save(tagModelEntity);
                tagModelEntityList.add(tagModelEntity);
            }
        }
        metricModelEntity.setTagModelEntities(tagModelEntityList);
        metricRepository.save(metricModelEntity);
    }

    public List<MetricModel> getAllMetric(Pageable pageable) {
        List<MetricModelEntity> metricModelEntities = metricRepository.findAll(pageable);
        List<MetricModel> metricModelList = new ArrayList<>();
        metricModelEntities.forEach(metricModelEntity -> metricModelList.add(converterDTOService.metricModelConverter(metricModelEntity)));
        return metricModelList;
    }


}
