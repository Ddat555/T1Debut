package com.example.MetricService.repositories;

import com.example.MetricService.models.TagModelEntity;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<TagModelEntity, Integer> {
}
