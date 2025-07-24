package com.example.MetricService.repositories;

import com.example.MetricService.models.MetricModelEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MetricRepository extends CrudRepository<MetricModelEntity, Integer> {
    List<MetricModelEntity> findAll(Pageable pageable);
}
