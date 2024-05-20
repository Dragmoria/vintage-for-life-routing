package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "algorithm_iterations_experiment")
@Getter
@Setter
@NoArgsConstructor
public class AlgorithmIterationsExperiment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "iterations")
    private Integer iterations;

    @Column(name = "runtime_in_seconds")
    private Long runtimeInSeconds;

    @Column(name = "fitness_value")
    private Double fitnessValue;

    @Column(name = "total_distance")
    private Integer totalDistance;

    @Column(name = "total_trucks")
    private Integer totalTrucks;

    @Column(name = "unique_chromosome_seen")
    private Integer uniqueChromosomeSeen;
}
