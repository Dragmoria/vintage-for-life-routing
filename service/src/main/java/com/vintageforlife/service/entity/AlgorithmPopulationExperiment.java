package com.vintageforlife.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "algorithm_population_experiment")
@Setter
@Getter
@NoArgsConstructor
public class AlgorithmPopulationExperiment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "population_size")
    private Integer populationSize;

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
