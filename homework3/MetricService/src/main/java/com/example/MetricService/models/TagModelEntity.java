package com.example.MetricService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TagModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String value;
    @ManyToOne(fetch = FetchType.LAZY)
    private MetricModelEntity metricModelEntity;

    public TagModelEntity(String name, String value, MetricModelEntity metricModelEntity) {
        this.name = name;
        this.value = value;
        this.metricModelEntity = metricModelEntity;
    }

    @Override
    public String toString() {
        return "TagModelEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", metricModelEntity=" + metricModelEntity +
                '}';
    }
}
