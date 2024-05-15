package com.vintageforlife.service.controller;

import com.vintageforlife.service.dto.RouteDTO;
import com.vintageforlife.service.services.database.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/planner")
public class PlannerController {
    private final RouteService routeService;

    @Autowired
    public PlannerController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping(value = "/allroutes", produces = "application/json")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<RouteDTO>> getAllRoutes() {
        List<RouteDTO> routes = routeService.getAllRoutes();
        return ResponseEntity.ok(routes);
    }
}
