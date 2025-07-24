package com.example.MetricService.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class MetricModelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private double value;
    @OneToMany
    private List<TagModelEntity> tagModelEntities;
    @Column(nullable = false)
    private LocalDateTime localDateTime;

    public MetricModelEntity(String name, double value, List<TagModelEntity> tagModelEntities, LocalDateTime localDateTime) {
        this.name = name;
        this.value = value;
        this.tagModelEntities = tagModelEntities;
        this.localDateTime = localDateTime;
    }

    public MetricModelEntity(String name, double value, LocalDateTime localDateTime) {
        this.name = name;
        this.value = value;
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "MetricModelEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", tagModelEntities=" + tagModelEntities +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
