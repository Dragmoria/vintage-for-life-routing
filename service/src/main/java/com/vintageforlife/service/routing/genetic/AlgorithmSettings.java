package com.vintageforlife.service.routing.genetic;

import com.vintageforlife.service.dto.TransportSettingDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AlgorithmSettings {
    private float truckWidth;

    private float truckLength;

    private double mutationRate;

    private double distanceWeight;

    private double trucksUsedWeight;

    private int populationSize;

    private int amountOfGenerations;

    public AlgorithmSettings(List<TransportSettingDTO> transportSettings) {
        this.truckWidth = Float.parseFloat(transportSettings.stream()
                .filter(setting -> setting.getName().equals("truck_width"))
                .map(TransportSettingDTO::getValue)
                .findFirst()
                .orElse("3"));

        this.truckLength = Float.parseFloat(transportSettings.stream()
                .filter(setting -> setting.getName().equals("truck_depth"))
                .map(TransportSettingDTO::getValue)
                .findFirst()
                .orElse("5"));

        this.mutationRate = Double.parseDouble(transportSettings.stream()
                .filter(setting -> setting.getName().equals("mutation_rate"))
                .map(TransportSettingDTO::getValue)
                .findFirst()
                .orElse("0.4"));

        this.distanceWeight = Double.parseDouble(transportSettings.stream()
                .filter(setting -> setting.getName().equals("distance_weight"))
                .map(TransportSettingDTO::getValue)
                .findFirst()
                .orElse("0.8"));

        this.trucksUsedWeight = Double.parseDouble(transportSettings.stream()
                .filter(setting -> setting.getName().equals("trucks_used_weight"))
                .map(TransportSettingDTO::getValue)
                .findFirst()
                .orElse("0.2"));

        this.populationSize = Integer.parseInt(transportSettings.stream()
                .filter(setting -> setting.getName().equals("population_size"))
                .map(TransportSettingDTO::getValue)
                .findFirst()
                .orElse("200"));

        this.amountOfGenerations = Integer.parseInt(transportSettings.stream()
                .filter(setting -> setting.getName().equals("amount_of_generations"))
                .map(TransportSettingDTO::getValue)
                .findFirst()
                .orElse("100"));
    }

    public AlgorithmSettings() {
    }
}
