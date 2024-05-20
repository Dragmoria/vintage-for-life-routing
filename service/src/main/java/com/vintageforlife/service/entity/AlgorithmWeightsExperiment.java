package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "algorithm_weights_experiment")
@Getter
@Setter
public class AlgorithmWeightsExperiment {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "distance_weight")
    private Double distanceWeight;

    @Column(name = "trucks_used_weight")
    private Double trucksUsedWeight;

    @Column(name = "fitness_value")
    private Double fitnessValue;

    @Column(name = "runtime_in_seconds")
    private Long runtimeInSeconds;

    @Column(name = "total_distance")
    private Integer totalDistance;

    @Column(name = "total_trucks")
    private Integer totalTrucks;

    @Column(name = "unique_chromosome_seen")
    private Integer uniqueChromosomeSeen;
}
