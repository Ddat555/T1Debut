package com.example.MetricService.services;

import com.example.MetricService.models.MetricModelEntity;
import com.example.MetricService.models.TagModelEntity;
import com.example.MetricService.models.dto.MetricModel;
import com.example.MetricService.models.dto.TagModel;
import com.example.MetricService.repositories.MetricRepository;
import com.example.MetricService.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConverterDTOService {

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private TagRepository tagRepository;


    public MetricModel metricModelConverter(MetricModelEntity metricModelEntity) {
        MetricModel metricModel = new MetricModel();
        metricModel.setName(metricModelEntity.getName());
        metricModel.setValue(metricModelEntity.getValue());
        metricModel.setLocalDateTime(metricModelEntity.getLocalDateTime());
        if (metricModelEntity.getTagModelEntities() != null) {
            List<TagModel> tagModelList = new ArrayList<>();
            metricModelEntity.getTagModelEntities().forEach(tagModelEntity -> tagModelList.add(tagModelConverter(tagModelEntity)));
            metricModel.setTagModels(tagModelList);
        }
        return metricModel;
    }

    public TagModel tagModelConverter(TagModelEntity tagModelEntity) {
        TagModel tagModel = new TagModel();
        tagModel.setName(tagModelEntity.getName());
        tagModel.setValue(tagModelEntity.getValue());
        return tagModel;
    }
}
