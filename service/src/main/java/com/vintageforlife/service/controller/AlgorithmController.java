package com.vintageforlife.service.controller;

import com.vintageforlife.service.routing.genetic.AlgorithmExperiment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/algorithm")
public class AlgorithmController {
    private final AlgorithmExperiment algorithmExperiment;

    @Autowired
    public AlgorithmController(AlgorithmExperiment algorithmExperiment) {
        this.algorithmExperiment = algorithmExperiment;
    }

    @GetMapping("/start-nodes-experiment")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<String> startNodesExperiment() {
        algorithmExperiment.nodesExperiment();
        return ResponseEntity.ok("Experiment finished");
    }

    @GetMapping("/start-population-experiment")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<String> startPopulationExperiment() {
        algorithmExperiment.populationExperiment();
        return ResponseEntity.ok("Experiment finished");
    }

    @GetMapping("/start-iterations-experiment")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<String> startIterationsExperiment() {
        algorithmExperiment.iterationsExperiment();
        return ResponseEntity.ok("Experiment finished");
    }

    @GetMapping("/start-weights-experiment")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<String> startWeightsExperiment() {
        algorithmExperiment.weightsExperiment();
        return ResponseEntity.ok("Experiment finished");
    }

    @GetMapping("/start-mutationrate-experiment")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<String> startMutationRateExperiment() {
        algorithmExperiment.mutationRateExperiment();
        return ResponseEntity.ok("Experiment finished");
    }
}
